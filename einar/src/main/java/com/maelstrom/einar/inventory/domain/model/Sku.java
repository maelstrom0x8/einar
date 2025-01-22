package com.maelstrom.einar.inventory.domain.model;

import java.util.Objects;
import java.util.Random;

public record Sku(String value)
{

	public static Sku generate(Item item)
	{
		String name = Objects.requireNonNull(item.getDetails().name());
		String[] words = name.split("[\\s\\-]+");
		StringBuilder sku = new StringBuilder();

		for (int i = 0; i < Math.min(words.length, 3); i++)
		{
			String word = words[i];
			if (word.length() >= 3)
			{
				sku.append(word.substring(0, 3 - i).toUpperCase());
			} else
			{
				sku.append(word.toUpperCase());
			}
		}
		int dis = 12 - sku.length() - 1;
		Random random = new Random();
		int randomNumber = random.nextInt((int) (1 * Math.pow(10, dis)));
		sku.append(randomNumber);

		return new Sku(sku.toString());
	}

	@Override
	public String toString()
	{
		return value;
	}

	public static Sku valueOf(String sku)
	{
		return new Sku(sku);
	}
}
