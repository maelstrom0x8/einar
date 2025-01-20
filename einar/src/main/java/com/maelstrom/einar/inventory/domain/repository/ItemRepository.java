package com.maelstrom.einar.inventory.domain.repository;

import com.maelstrom.einar.common.data.GenericRepository;
import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.model.ItemId;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends GenericRepository<Item, ItemId>
{

	List<Item> findAllByInventoryId(InventoryId id);

	Optional<Item> findItemBySku(String sku);

	List<Item> findAllBelowStockThreshold();
}
