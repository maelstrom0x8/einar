package com.maelstrom.einar.inventory.domain.model;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Item
{
	private ItemId id;
	private InventoryId inventoryId;
	private String name;
	private String description;
	private Sku sku;
	private int stockThreshold;

	public Item() {}

	public Item(String name, String description, int stockThreshold) {
		this.name = name;
		this.description = description;
		this.stockThreshold = stockThreshold;

		this.sku = Sku.generate(this);
	}

	public ItemId getId()
	{
		return id;
	}

	void setId(ItemId id)
	{
		this.id = id;
	}

	InventoryId getInventoryId()
	{
		return inventoryId;
	}

	void setInventoryId(InventoryId inventoryId)
	{
		this.inventoryId = inventoryId;
	}

	String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	Sku getSku()
	{
		return sku;
	}

	void setSku(Sku sku)
	{
		this.sku = sku;
	}

	int getStockThreshold()
	{
		return stockThreshold;
	}

	void setStockThreshold(int stockThreshold)
	{
		this.stockThreshold = stockThreshold;
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
		return Objects.equals(id, item.id) && Objects.equals(inventoryId, item.inventoryId) && Objects.equals(name, item.name) && Objects.equals(sku, item.sku);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, inventoryId, name, sku);
	}
}
