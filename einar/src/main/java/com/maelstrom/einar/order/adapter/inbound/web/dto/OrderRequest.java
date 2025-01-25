package com.maelstrom.einar.order.adapter.inbound.web.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record OrderRequest(Long customerId, Map<String, Integer> items, LocalDateTime scheduleFor) {}
