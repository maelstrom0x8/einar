package com.maelstrom.einar.order.domain.model;

import org.springframework.context.ApplicationEvent;

public class OrderInitiatedEvent extends ApplicationEvent
{
	public OrderInitiatedEvent(Object source)
	{
		super(source);
	}

}
