package com.maelstrom.einar.inventory.application.service;


import com.maelstrom.einar.inventory.application.dto.NewItemRequest;
import com.maelstrom.einar.inventory.domain.InventoryNotFoundException;
import com.maelstrom.einar.inventory.domain.ItemNotFoundException;
import com.maelstrom.einar.inventory.domain.model.*;
import com.maelstrom.einar.inventory.domain.repository.InventoryRepository;
import com.maelstrom.einar.inventory.domain.service.StockStatisticsService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class InventoryService
{

	private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
	private final InventoryRepository inventoryRepository;
	private final StockStatisticsService stockStatisticsService;

	public InventoryService(InventoryRepository inventoryRepository, StockStatisticsService stockStatisticsService)
	{
		this.inventoryRepository = inventoryRepository;
		this.stockStatisticsService = stockStatisticsService;
	}

	@Transactional
	public List<StockDetail> statistics(@NotNull Set<Integer> itemsIds)
	{
		if (itemsIds.isEmpty())
			return Collections.emptyList();

		return stockStatisticsService.execute(itemsIds);
	}

	@Transactional
	public InventoryId createInventory(Inventory inventory)
	{
		var inventory_ = inventoryRepository.save(inventory);
		if (inventory_.getId() == null)
			throw new RuntimeException("Something went wrong. Try again.");
		return inventory_.getId();
	}

	@Transactional
	public void removeInventory(InventoryId id)
	{
		Inventory inventory = getInventoryById(id);
		inventory.setState(InventoryState.CLOSED);
		inventoryRepository.save(inventory);
	}

	public Inventory getInventoryById(InventoryId inventoryId)
	{
		return inventoryRepository.findById(inventoryId).orElseThrow(InventoryNotFoundException::new);
	}

	public List<Item> getItems(InventoryId id, int offset, int count)
	{
		offset = Math.max(offset, 0);
		count = Math.max(count, 0);
		if (count == 0)
			return null;

		List<Item> items = inventoryRepository.findItemsByInventoryId(id, offset, count);

		return items;
	}

	public Item getItem(ItemId id)
	{
		Item item = inventoryRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);
		log.info("Found item with id {}", item.getId());
		return item;
	}

	public List<ItemId> addItemsToInventory(InventoryId inventoryId, @NotNull @NotEmpty Collection<NewItemRequest> request) throws IllegalAccessException
	{
		Inventory inventory = getInventoryById(inventoryId);
		List<Item> items = request.stream().map(e -> new Item(e.name(), e.description(), e.stockThreshold()))
						.toList();
		inventory.addItems(items);
		inventoryRepository.saveItems(inventory, items);
		return inventory.getItems();
	}


}
