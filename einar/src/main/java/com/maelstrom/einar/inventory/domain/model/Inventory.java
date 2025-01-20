package com.maelstrom.einar.inventory.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.*;

public class Inventory
{
	private final List<ItemId> items = new ArrayList<>();
	private InventoryId id;
	private Integer accountId;
	private String name;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdated;
	private InventoryState state = InventoryState.OPEN;

	private Inventory() {}

	Inventory(Integer accountId, @NotBlank String name, @NotBlank String description)
	{
		this.accountId = accountId;
		this.name = name;
		this.description = description;
	}

	public static Inventory open(Integer accountId, String name, String description)
	{
		return new Inventory(accountId, name, description);
	}

	public void setId(InventoryId id)
	{
		this.id = id;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public void setLastUpdated(LocalDateTime lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}





	public void addItem(@NotNull Item item) throws IllegalAccessException
	{
		if (state.equals(InventoryState.ARCHIVED) || state.equals(InventoryState.CLOSED))
			throw new IllegalAccessException("Unable to modify this inventory. Inventory is " + this.state);

		if (items.contains(item.getId()))
			throw new IllegalStateException("Cannot add duplicate items to inventory");
		item.assignToInventory(this.id);
		items.add(item.getId());
	}

	public void addItems(@NotNull @NotEmpty Collection<Item> items) throws IllegalArgumentException, IllegalAccessException
	{
		for (Item item : items) {
			addItem(item);
		}
	}

	public InventoryState getState()
	{
		return state;
	}

	public void setState(InventoryState state)
	{
		if(this.state.equals(InventoryState.CLOSED))
			throw new IllegalStateException("Cannot modify this inventory");
		this.state = state;
	}

	public void rename(@NotBlank String name)
	{
		this.name = name;
	}

	public InventoryId getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	public Integer getAccountId()
	{
		return accountId;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public LocalDateTime getLastUpdated()
	{
		return lastUpdated;
	}

	public List<ItemId> getItems()
	{
		return Collections.unmodifiableList(items);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "Inventory{" +
						"id=" + id +
						", accountId=" + accountId +
						", name='" + name + '\'' +
						", items=" + items +
						", createdAt=" + createdAt +
						", lastUpdated=" + lastUpdated +
						'}';
	}
}
