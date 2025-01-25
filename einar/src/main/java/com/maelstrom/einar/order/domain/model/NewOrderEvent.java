package com.maelstrom.einar.order.domain.model;

import org.springframework.context.ApplicationEvent;

public class NewOrderEvent extends ApplicationEvent
{
	public NewOrderEvent(Object source)
	{
		super(source);
	}
}
