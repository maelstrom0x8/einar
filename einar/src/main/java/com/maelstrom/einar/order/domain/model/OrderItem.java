package com.maelstrom.einar.order.domain.model;

public class OrderItem
{
	private OrderItemId id;
	private Integer quantity;

	public OrderItem(String itemId,  OrderId orderId,  Integer quantity)
	{
		this.quantity = quantity;
		this.id = new OrderItemId(itemId, orderId);
	}

	public OrderId getOrderId()
	{
		return id.orderId();
	}

	public void setId(OrderItemId id)
	{
		this.id = id;
	}

	public void setOrderId(OrderId orderId)
	{
		this.id = new OrderItemId(id.itemId(), orderId);
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}

	public OrderItemId getId()
	{
		return id;
	}

	public String getItemId()
	{
		return id.itemId();
	}
}
