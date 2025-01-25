package com.maelstrom.einar.order.application;

import com.maelstrom.einar.order.domain.model.*;
import com.maelstrom.einar.order.domain.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class OrderService
{
	private final OrderRepository orderRepository;
	private final ApplicationEventPublisher eventPublisher;

	public OrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher)
	{
		this.orderRepository = orderRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public void placeOrder(Integer accountId, OrderSpecification orderSpec)
	{
		eventPublisher.publishEvent(new OrderInitiatedEvent(orderSpec.getCustomerId()));

		var items = orderSpec.getItems().entrySet()
			.stream().map(entry ->
				new OrderItem(entry.getKey(), null, entry.getValue())).collect(Collectors.toSet());

		Order order = new Order();
		order.addAllItems(items);

		OrderId orderId = orderRepository.save(order).getId();

		eventPublisher.publishEvent(new NewOrderEvent(new PlacedOrder(accountId, orderId, orderSpec.getCustomerId())));

	}


}
