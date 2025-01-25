package com.maelstrom.einar.order.application;

import com.maelstrom.einar.order.domain.model.OrderId;

public record PlacedOrder(Integer accountId, OrderId id, Long customerId)
{
}
