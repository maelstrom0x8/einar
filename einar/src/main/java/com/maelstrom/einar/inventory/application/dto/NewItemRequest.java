package com.maelstrom.einar.inventory.application.dto;

public record NewItemRequest(String name, String description, int stockThreshold) {}
