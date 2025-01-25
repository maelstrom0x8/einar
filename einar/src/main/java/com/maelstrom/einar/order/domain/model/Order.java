package com.maelstrom.einar.order.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Order
{
	private OrderId id;
	private OrderStatus status = OrderStatus.NEW;
	private LocalDateTime createdAt;
	private LocalDateTime lastUpdated;

	private final Set<OrderItem> items = new HashSet<>();

	public Order()
	{
		this.createdAt = LocalDateTime.now();
		this.lastUpdated = LocalDateTime.now();
	}

	public static Order withOrderItems(Set<OrderItem> items)
	{
		Order order = new Order();
		order.addAllItems(items);
		return order;
	}

	public Set<OrderItem> getItems()
	{
		return Collections.unmodifiableSet(items);
	}

	public void addItem(OrderItem item)
	{
		item.setOrderId(id);
		items.add(item);
	}

	public void setStatus(OrderStatus status)
	{
		if (status.equals(OrderStatus.CANCELLED) && this.status.equals(OrderStatus.COMPLETED))
			throw new IllegalStateException("Cannot cancel a completed order");
		this.status = status;
	}

	public void addAllItems(Set<OrderItem> items)
	{
		for (OrderItem item : items)
		{
			addItem(item);
		}
	}

	public void updateOrderItems(Set<OrderItem> items)
	{
		if(status.equals(OrderStatus.COMPLETED) || status.equals(OrderStatus.CANCELLED))
			throw new IllegalStateException("Cannot update a completed or cancelled order");
		this.items.clear();
		this.items.addAll(items);
	}

	public OrderId getId()
	{
		return id;
	}

	public void setId(OrderId id)
	{
		this.id = id;
	}

	public OrderStatus getStatus()
	{
		return status;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
}
