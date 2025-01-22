package com.maelstrom.einar.inventory.application.service;


import com.maelstrom.einar.inventory.domain.InventoryNotFoundException;
import com.maelstrom.einar.inventory.domain.ItemNotFoundException;
import com.maelstrom.einar.inventory.domain.model.*;
import com.maelstrom.einar.inventory.domain.repository.InventoryRepository;
import com.maelstrom.einar.inventory.domain.repository.ItemRepository;
import com.maelstrom.einar.inventory.domain.service.StockStatisticsService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class InventoryService
{

	private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
	private final InventoryRepository inventoryRepository;
	private final ItemRepository itemRepository;
	private final StockStatisticsService stockStatisticsService;

	public InventoryService(InventoryRepository inventoryRepository, ItemRepository itemRepository,
													StockStatisticsService stockStatisticsService)
	{
		this.inventoryRepository = inventoryRepository;
		this.itemRepository = itemRepository;
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

	public void setInventoryState(InventoryId id, InventoryState state)
	{
		Inventory inventory = getInventoryById(id);
		inventory.setState(state);
		inventoryRepository.save(inventory);
	}

	public Inventory getInventoryById(InventoryId inventoryId)
	{
		return inventoryRepository.findById(inventoryId).orElseThrow(InventoryNotFoundException::new);
	}

	public List<Item> getItems(InventoryId id, int offset, int count)
	{
		Inventory inventory = getInventoryById(id);
		return itemRepository.findAllByInventoryId(inventory.getId());
	}

	public Item getItem(ItemId id)
	{
		Item item = inventoryRepository.findItemById(id).orElseThrow(ItemNotFoundException::new);
		log.info("Found item with id {}", item.getId());
		return item;
	}

	public void addItemsToInventory(InventoryId inventoryId, @NotNull @NotEmpty List<Item> items) throws IllegalAccessException
	{
		if(!items.stream().allMatch(item -> item.getInventoryId().equals(inventoryId))) {
			throw new IllegalAccessException("Items must belong to the inventory");
		}
		for (Item item : items)
		{
			itemRepository.save(item);
		}
	}

	public void updateItem(ItemId itemId, String name, String description, int stockThreshold)
	{
		itemRepository.findById(itemId).ifPresent(item ->
		{
			item.updateDetails(name, description, stockThreshold);
			itemRepository.save(item);
		});
	}

	public void deleteInventory(InventoryId id)
	{
		inventoryRepository.findById(id).ifPresent(inventory ->
		{
			inventory.setState(InventoryState.CLOSED);
			inventoryRepository.save(inventory);
		});
	}

	public void removeItem(ItemId itemId)
	{
		log.warn("Removing item with id {}", itemId);
		itemRepository.deleteById(itemId);
	}

	public List<Inventory> getAllInventories(Integer accountId)
	{
		return inventoryRepository.findAllByAccountId(accountId);
	}
}
