package com.maelstrom.einar.supplier.domain;

import org.springframework.context.ApplicationEvent;

public class SupplierRegisteredEvent extends ApplicationEvent
{
	public SupplierRegisteredEvent(Integer accountId)
	{
		super(accountId);
	}
}
