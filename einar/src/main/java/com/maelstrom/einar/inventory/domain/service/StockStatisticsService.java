package com.maelstrom.einar.inventory.domain.service;

import com.maelstrom.einar.inventory.domain.model.StockDetail;
import com.maelstrom.einar.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class StockStatisticsService
{

	private final InventoryRepository inventoryRepository;

	public StockStatisticsService(InventoryRepository inventoryRepository) {this.inventoryRepository = inventoryRepository;}

	public List<StockDetail> execute(Set<Integer> ids)
	{
		return Collections.emptyList();
	}

}
