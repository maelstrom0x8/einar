package com.maelstrom.einar.inventory.domain.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class Item
{
	private ItemId id;
	private InventoryId inventoryId;
	private Details details;

	public static Item create(InventoryId inventoryId, String name, String description, int stockThreshold)
	{
		Item item = new Item(name, description, stockThreshold);
		item.inventoryId = inventoryId;
		return item;
	}

	private Item(String name, String description, int stockThreshold)
	{
		updateDetails(name, description, stockThreshold);
		var sku = Sku.generate(this);
		this.id = ItemId.of(null, sku);
	}

	public record  Details(String name, String description, int stockThreshold, LocalDateTime createdAt, LocalDateTime lastUpdated)
	{
	}

	public ItemId getId()
	{
		return id;
	}

	public void setId(ItemId id)
	{
		this.id = id;
	}

	public InventoryId getInventoryId()
	{
		return inventoryId;
	}

	public Details getDetails()
	{
		return details;
	}

	public void updateDetails(String name, String description, int stockThreshold)
	{
		this.details = new Details(name, description, stockThreshold, LocalDateTime.now(), null);
	}

	public void assignToInventory(@NotNull InventoryId inventoryId)
	{
		this.inventoryId = inventoryId;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Item item)) return false;
		return Objects.equals(id, item.id) && Objects.equals(inventoryId, item.inventoryId)
			&& Objects.equals(details, item.details) && Objects.equals(id.sku(), item.id.sku());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, inventoryId);
	}
}
