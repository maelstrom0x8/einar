package com.maelstrom.einar.inventory.adapter.outbound.infrastructure.persistence;

import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;
import com.maelstrom.einar.inventory.domain.repository.ItemRepository;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record9;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.maelstrom.jooq.tables.Inventories.INVENTORIES;
import static com.maelstrom.jooq.tables.Items.ITEMS;


@Repository
public class ItemRepositoryAdapter implements ItemRepository
{
	private final DSLContext ctx;

	ItemRepositoryAdapter(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public Item save(Item item)
	{

		Record1<Integer> _r = ctx.insertInto(ITEMS)
			.set(ITEMS.SKU, item.getId().sku().value())
			.set(ITEMS.NAME, item.getName())
			.set(ITEMS.DESCRIPTION, item.getDescription())
			.set(ITEMS.BMIN, item.getMin())
			.set(ITEMS.BMAX, item.getMax())
			.set(ITEMS.CREATED_AT, item.getCreatedAt() != null ? item.getCreatedAt() : LocalDateTime.now())
			.set(ITEMS.INVENTORY_ID, item.getInventoryId().id())
			.set(ITEMS.LAST_UPDATED,  LocalDateTime.now())
			.returningResult(ITEMS.ITEM_ID).fetchOne();
		return item;
	}

	@Override
	public Optional<Item> findById(ItemId itemId)
	{
		if (itemId == null)
			throw new IllegalArgumentException("itemId cannot be null");

		var _acctId = itemId.inventoryId().accountId();
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.BMIN, ITEMS.BMAX,
				ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.join(INVENTORIES).on(ITEMS.INVENTORY_ID.eq(INVENTORIES.INVENTORY_ID))
			.where(ITEMS.SKU.eq(itemId.sku().value()).and(INVENTORIES.ACCOUNT_ID.eq(_acctId)))
			.fetchOptional().map(mapToItem);
	}

	@Override
	public List<Item> findAll()
	{
		return List.of();
	}

	private static final
	Function<? super Record9<Integer, Integer, String, String, String, Integer, Integer, LocalDateTime, LocalDateTime>,
			? extends Item>
		mapToItem = r ->
	{
		Item item = new Item(new InventoryId(r.get(ITEMS.INVENTORY_ID), null),
			r.get(ITEMS.NAME), r.get(ITEMS.DESCRIPTION), r.get(ITEMS.BMIN), r.get(ITEMS.BMAX));
		item.setId(ItemId.of(r.get(ITEMS.SKU), new InventoryId(r.get(ITEMS.INVENTORY_ID), null)));
		return item;
	};

	@Override
	public List<Item> findAllByInventoryId(InventoryId inventoryId)
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.BMIN, ITEMS.BMAX,
				ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS).where(ITEMS.INVENTORY_ID.eq(inventoryId.id()))
			.fetch().map(mapToItem::apply);
	}

	@Override
	public void deleteById(ItemId itemId)
	{
		ctx.deleteFrom(ITEMS)
			.where(ITEMS.SKU.eq(itemId.sku().toString()))
			.execute();
	}

	@Override
	public Optional<Item> findItemBySku(String sku)
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.BMIN, ITEMS.BMAX,
				ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.SKU.eq(sku))
			.fetchOptional().map(mapToItem);
	}

	@Override
	public List<Item> findAllWithinThreshold()
	{
		int count = ctx.fetchCount(ITEMS);
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.BMIN, ITEMS.BMAX, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.BMIN.lessOrEqual(count)).and(ITEMS.BMAX.greaterOrEqual(count))
			.fetch().map(mapToItem::apply);
	}
}
