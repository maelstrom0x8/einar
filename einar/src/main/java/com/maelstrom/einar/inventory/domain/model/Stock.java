package com.maelstrom.einar.inventory.domain.model;

public class Stock
{
	private InventoryId inventoryId;
	private ItemId itemId;

	public Stock(InventoryId inventoryId, ItemId itemId)
	{
		this.inventoryId = inventoryId;
		this.itemId = itemId;
	}
}
