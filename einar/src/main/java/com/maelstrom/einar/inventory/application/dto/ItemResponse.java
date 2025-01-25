package com.maelstrom.einar.inventory.application.dto;

import com.maelstrom.einar.inventory.domain.model.Item;

import java.time.LocalDateTime;

public record ItemResponse(String sku, LocalDateTime createdAt, LocalDateTime lastUpdated) {

	public static ItemResponse from(Item item) {
		return new ItemResponse(item.getId().sku().value(), item.getDetails().createdAt(), item.getDetails().lastUpdated());
	}
}
