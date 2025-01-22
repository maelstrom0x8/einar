package com.maelstrom.einar.inventory.adapter.outbound.infrastructure.persistence;

import com.maelstrom.config.TenantContext;
import com.maelstrom.einar.inventory.domain.model.*;
import com.maelstrom.einar.inventory.domain.repository.InventoryRepository;
import com.maelstrom.jooq.enums.State;
import com.maelstrom.jooq.tables.records.InventoriesRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.maelstrom.jooq.tables.Inventories.INVENTORIES;
import static org.jooq.impl.DSL.asterisk;

@Repository
class InventoryRepositoryAdapter implements InventoryRepository
{

	private final DSLContext ctx;

	public InventoryRepositoryAdapter(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public Inventory save(Inventory inventory)
	{
		Integer currentTenant = TenantContext.getCurrentTenant();
		State state = State.valueOf(State.class, inventory.getState().name());
		InventoriesRecord record_ = ctx.insertInto(INVENTORIES, INVENTORIES.NAME, INVENTORIES.DESCRIPTION, INVENTORIES.ACCOUNT_ID,
				INVENTORIES.STATE, INVENTORIES.CREATED_AT, INVENTORIES.LAST_UPDATED, INVENTORIES.TENANT_ID)
			.values(inventory.getName(), inventory.getDescription(), inventory.getAccountId(), state,
				inventory.getCreatedAt(), LocalDateTime.now(), currentTenant)
			.returning(INVENTORIES.INVENTORY_ID).fetchOne();

		InventoriesRecord record_1 = Objects.requireNonNull(record_);
		inventory.setId(new InventoryId(record_1.getInventoryId(), inventory.getAccountId()));
		inventory.setCreatedAt(record_1.getCreatedAt());
		inventory.setLastUpdated(record_1.getLastUpdated());
		inventory.setDescription(record_1.getDescription());

		return inventory;

	}

	@Override
	public Optional<Inventory> findById(InventoryId id)
	{
		if (id == null)
			throw new NullPointerException("id cannot be null");
		var inventory = ctx.select(asterisk())
			.from(INVENTORIES)
			.where(INVENTORIES.INVENTORY_ID.eq(id.id())).fetchOneInto(Inventory.class);
		if (inventory != null)
			inventory.setId(id);
		return Optional.ofNullable(inventory);
	}

	@Override
	public List<Inventory> findAll()
	{
		return ctx.select(INVENTORIES.INVENTORY_ID, INVENTORIES.ACCOUNT_ID, INVENTORIES.NAME, INVENTORIES.DESCRIPTION,
				INVENTORIES.CREATED_AT, INVENTORIES.LAST_UPDATED, INVENTORIES.STATE)
			.from(INVENTORIES)
			.fetch().map(e ->
			{
				var acctId = e.get(INVENTORIES.ACCOUNT_ID);
				var invId = e.get(INVENTORIES.INVENTORY_ID);
				Inventory inventory = Inventory.open(acctId, e.get(INVENTORIES.NAME),
					e.get(INVENTORIES.DESCRIPTION));
				var state = State.valueOf(InventoryState.class, e.get(INVENTORIES.STATE).name());
				inventory.setState(state);
				inventory.setId(new InventoryId(invId, acctId));
				inventory.setCreatedAt(e.get(INVENTORIES.CREATED_AT));
				inventory.setLastUpdated(e.get(INVENTORIES.LAST_UPDATED));

				return inventory;
			});

	}

	@Override
	public void deleteById(InventoryId inventoryId)
	{
		ctx.deleteFrom(INVENTORIES)
			.where(INVENTORIES.INVENTORY_ID.eq(inventoryId.id()))
			.execute();
	}

	@Override
	public List<Item> findItemsByInventoryId(InventoryId id, int offset, int count)
	{
		return List.of();
	}

	@Override
	public Optional<Item> findItemBySku(String sku)
	{
		return Optional.empty();
	}

	@Override
	public Optional<Item> findItemById(ItemId id)
	{
		return Optional.empty();
	}

	@Override
	public List<Item> saveItems(Inventory inventory, List<Item> items)
	{
		return List.of();
	}

	@Override
	public List<Inventory> findAllByAccountId(Integer accountId)
	{
		ctx.selectFrom(INVENTORIES).where(INVENTORIES.ACCOUNT_ID.eq(accountId))
			.fetch().map(e ->
			{
				var acctId = e.get(INVENTORIES.ACCOUNT_ID);
				var invId = e.get(INVENTORIES.INVENTORY_ID);
				Inventory inventory = Inventory.open(acctId, e.get(INVENTORIES.NAME),
					e.get(INVENTORIES.DESCRIPTION));
				var state = State.valueOf(InventoryState.class, e.get(INVENTORIES.STATE).name());
				inventory.setState(state);
				inventory.setId(new InventoryId(invId, acctId));
				inventory.setCreatedAt(e.get(INVENTORIES.CREATED_AT));
				inventory.setLastUpdated(e.get(INVENTORIES.LAST_UPDATED));

				return inventory;
			});
		return List.of();
	}

}
