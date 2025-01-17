package com.maelstrom.einar.inventory.adapter.outbound.infrastructure.persistence;

import com.maelstrom.einar.inventory.domain.model.Inventory;
import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;
import com.maelstrom.einar.inventory.domain.repository.InventoryRepository;
import com.maelstrom.jooq.tables.records.InventoriesRecord;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

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
		InventoriesRecord record_ = ctx.insertInto(INVENTORIES, INVENTORIES.NAME, INVENTORIES.ACCOUNT_ID, INVENTORIES.LAST_UPDATED)
						.values(inventory.getName(), inventory.getAccountId(), inventory.getLastUpdated())
						.returning(INVENTORIES.INVENTORY_ID).fetchOne();

		InventoriesRecord record_1 = Objects.requireNonNull(record_);
		return findById(inventory.getId()).orElseThrow(() -> new DataAccessException("No such record with key " + record_1.getInventoryId()));

	}

	@Override
	public Optional<Inventory> findById(InventoryId id)
	{
		if (id == null)
			throw new NullPointerException("id cannot be null");
		var inventory = ctx.select(asterisk())
						.from(INVENTORIES)
						.where(INVENTORIES.INVENTORY_ID.eq(id.id())).fetchOneInto(Inventory.class);

		return Optional.ofNullable(inventory);
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

}
