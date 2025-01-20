package com.maelstrom.einar.inventory.domain.model;

public record Sku(String value)
{

	public static Sku generate(Item item)
	{
		return null;
	}

	public static Sku valueOf(String sku)
	{
		return new Sku(sku);
	}
}
