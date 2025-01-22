package com.maelstrom.einar.inventory.domain.model;

public record ItemId(Sku sku, InventoryId inventoryId)
{
	public static ItemId of(String sku, InventoryId inventoryId)
	{
		return new ItemId(Sku.valueOf(sku), inventoryId);
	}
}
