package com.maelstrom.einar.inventory.domain.model;

import org.springframework.context.ApplicationEvent;

public class ThresholdEvent extends ApplicationEvent
{
	public ThresholdEvent(Object coll)
	{
		super(coll);
	}
}
