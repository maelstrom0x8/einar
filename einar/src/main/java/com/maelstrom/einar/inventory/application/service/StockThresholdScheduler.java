package com.maelstrom.einar.inventory.application.service;

import com.maelstrom.einar.inventory.domain.model.InventoryId;
import com.maelstrom.einar.inventory.domain.model.Item;
import com.maelstrom.einar.inventory.domain.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class StockThresholdScheduler
{
	private static final Logger log = LoggerFactory.getLogger(StockThresholdScheduler.class);
	private final ItemRepository itemRepository;
	private final ApplicationEventPublisher eventPublisher;

	public StockThresholdScheduler(ItemRepository itemRepository, ApplicationEventPublisher eventPublisher)
	{
		this.itemRepository = itemRepository;
		this.eventPublisher = eventPublisher;
	}

	@Scheduled(cron = "${einar.inventory.stock-monitoring.cron='0 0 8 * * *'}")
	public void scan() {
		log.info("Scanning for items withing stock threshold boundaries");
		List<Item> items = itemRepository.findAllBelowStockThreshold();
		Map<InventoryId, List<Item>> _coll = items.stream().collect(groupingBy(Item::getInventoryId));
		int _count = _coll.values().stream().mapToInt(List::size).sum();
		log.info("{} items have reached threshold boundary", _count);

		eventPublisher.publishEvent(null);
	}
}
