package com.maelstrom.einar.order.domain.repository;

import com.maelstrom.einar.common.data.GenericRepository;
import com.maelstrom.einar.order.domain.model.Order;
import com.maelstrom.einar.order.domain.model.OrderId;

public interface OrderRepository extends GenericRepository<Order, OrderId> {}
