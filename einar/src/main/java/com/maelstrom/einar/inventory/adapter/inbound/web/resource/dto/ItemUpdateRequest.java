package com.maelstrom.einar.inventory.adapter.inbound.web.resource.dto;

public record ItemUpdateRequest(String name, String description, int min, int max)
{
}
