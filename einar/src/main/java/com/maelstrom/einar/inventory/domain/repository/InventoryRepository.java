package com.maelstrom.einar.inventory.domain.repository;

import com.maelstrom.einar.inventory.domain.model.Inventory;
import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository
{
	Inventory save(Inventory inventory);

	Optional<Inventory> findById(InventoryId id);

	List<Item> findItemsByInventoryId(InventoryId id, int offset, int count);

	Optional<Item> findItemBySku(String sku);

	Optional<Item> findItemById(ItemId id);

	List<Item> saveItems(Inventory inventory, List<Item> items);
}
