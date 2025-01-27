package com.maelstrom.einar.inventory.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@Data
public class Item
{
	private ItemId id;
	private InventoryId inventoryId;
	private Details details;
	private String name;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdated;
	private int min;
	private int max;


	public Item(InventoryId inventoryId, String name, String description, int min, int max)
	{
		this.inventoryId = inventoryId;
		this.name = name;
		this.description = description;
		this.min = min;
		this.max = max;
	}

	public void updateDetails(String name, String description, int min, int max)
	{
		this.name = name;
		this.description = description;
		this.min = min;
		this.max = max;
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
