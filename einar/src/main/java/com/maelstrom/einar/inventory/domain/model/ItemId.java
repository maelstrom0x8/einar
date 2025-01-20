package com.maelstrom.einar.inventory.domain.model;

public record ItemId(Integer id, Sku sku)
{
	public static ItemId of(Integer value, Sku sku)
	{
		return new ItemId(value, sku);
	}
}
