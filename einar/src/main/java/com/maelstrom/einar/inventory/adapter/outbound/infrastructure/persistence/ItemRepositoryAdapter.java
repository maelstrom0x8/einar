package com.maelstrom.einar.inventory.adapter.outbound.infrastructure.persistence;

import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;
import com.maelstrom.einar.inventory.domain.model.Sku;
import com.maelstrom.einar.inventory.domain.repository.ItemRepository;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record7;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
			.set(ITEMS.ITEM_ID, item.getId().id())
			.set(ITEMS.SKU, item.getId().sku().toString())
			.set(ITEMS.NAME, details.name())
			.set(ITEMS.DESCRIPTION, details.description())
			.set(ITEMS.THRESHOLD, details.stockThreshold())
			.set(ITEMS.CREATED_AT, details.createdAt())
			.set(ITEMS.INVENTORY_ID, item.getInventoryId().id())
			.set(ITEMS.LAST_UPDATED, details.lastUpdated())
			.returningResult(ITEMS.ITEM_ID).fetchOne();

		if (_r != null)
			item.setId(ItemId.of(_r.value1(), item.getId().sku()));

		return null;
	}

	@Override
	public Optional<Item> findById(ItemId itemId)
	{
		if (itemId == null)
			throw new IllegalArgumentException("itemId cannot be null");
		return ctx.select(ITEMS.ITEM_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.ITEM_ID.eq(itemId.id()).or(ITEMS.SKU.eq(itemId.sku().toString())))
			.fetchOptional().map(mapToItem);
	}

	private static final Function<Record7<Integer, String, String, String, Integer, LocalDateTime, LocalDateTime>, Item> mapToItem = r ->
	{
		Item item = Item.create(new InventoryId(r.get(ITEMS.INVENTORY_ID), null),
			r.get(ITEMS.NAME), r.get(ITEMS.DESCRIPTION), r.get(ITEMS.THRESHOLD));
		item.setId(ItemId.of(r.get(ITEMS.ITEM_ID), Sku.valueOf(r.get(ITEMS.SKU))));
		return item;
	};

	@Override
	public List<Item> findAllByInventoryId(InventoryId inventoryId)
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS).where(ITEMS.INVENTORY_ID.eq(inventoryId.id()))
			.fetch().map(mapToItem::apply);
	}

	@Override
	public void deleteById(ItemId itemId)
	{
		ctx.deleteFrom(ITEMS)
			.where(ITEMS.ITEM_ID.eq(itemId.id()).or(ITEMS.SKU.eq(itemId.sku().toString())))
			.execute();
	}

	@Override
	public Optional<Item> findItemBySku(String sku)
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.SKU.eq(sku))
			.fetchOptional().map(mapToItem);
	}

	@Override
	public List<Item> findAllBelowStockThreshold()
	{
		return ctx.select(ITEMS.ITEM_ID, ITEMS.SKU, ITEMS.NAME, ITEMS.DESCRIPTION, ITEMS.THRESHOLD, ITEMS.CREATED_AT, ITEMS.LAST_UPDATED)
			.from(ITEMS)
			.where(ITEMS.THRESHOLD.greaterThan(ITEMS.THRESHOLD))
			.fetch().map(mapToItem::apply);
	}
}
