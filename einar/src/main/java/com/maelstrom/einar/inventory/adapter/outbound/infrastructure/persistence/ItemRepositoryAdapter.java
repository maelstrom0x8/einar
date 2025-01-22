package com.maelstrom.einar.inventory.adapter.outbound.infrastructure.persistence;

import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;
import com.maelstrom.einar.inventory.domain.repository.ItemRepository;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record8;
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
		Item.Details details = item.getDetails();
		Record1<Integer> _r = ctx.insertInto(ITEMS)
			.set(ITEMS.SKU, item.getId().sku().toString())
			.set(ITEMS.NAME, details.name())
			.set(ITEMS.DESCRIPTION, details.description())
			.set(ITEMS.THRESHOLD, details.stockThreshold())
			.set(ITEMS.CREATED_AT, details.createdAt())
			.set(ITEMS.INVENTORY_ID, item.getInventoryId().id())
			.set(ITEMS.LAST_UPDATED, details.lastUpdated())
			.returningResult(ITEMS.ITEM_ID).fetchOne();

		return item;
	}

	@Override
	public Optional<Item> findById(ItemId itemId)
	{
		if (itemId == null)
			throw new IllegalArgumentException("itemId cannot be null");
//		var _invId = itemId.inventoryId().id();
		var _acctId = itemId.inventoryId().accountId();
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD,
				ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.join(INVENTORIES).on(ITEMS.INVENTORY_ID.eq(INVENTORIES.INVENTORY_ID))
			.where(ITEMS.SKU.eq(itemId.sku().toString()).and(INVENTORIES.ACCOUNT_ID.eq(_acctId)))
			.fetchOptional().map(mapToItem);
	}

	@Override
	public List<Item> findAll()
	{
		return List.of();
	}

	private static final Function<Record8<Integer, Integer, String, String, String, Integer, LocalDateTime, LocalDateTime>, Item> mapToItem = r ->
	{
		Item item = Item.create(new InventoryId(r.get(ITEMS.INVENTORY_ID), null),
			r.get(ITEMS.NAME), r.get(ITEMS.DESCRIPTION), r.get(ITEMS.THRESHOLD));
//		item.setId(ItemId.of(Sku.valueOf(r.get(ITEMS.SKU), new InventoryId(null, null))));
		return item;
	};

	@Override
	public List<Item> findAllByInventoryId(InventoryId inventoryId)
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD,
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
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD,
				ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.SKU.eq(sku))
			.fetchOptional().map(mapToItem);
	}

	@Override
	public List<Item> findAllBelowStockThreshold()
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.INVENTORY_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.THRESHOLD.greaterThan(ITEMS.THRESHOLD))
			.fetch().map(mapToItem::apply);
	}
}
