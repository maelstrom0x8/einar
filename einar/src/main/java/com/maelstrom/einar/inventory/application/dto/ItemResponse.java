package com.maelstrom.einar.inventory.application.dto;

import com.maelstrom.einar.inventory.domain.model.Item;

public record ItemResponse() {

	public static ItemResponse from(Item item) {return null;}
}
