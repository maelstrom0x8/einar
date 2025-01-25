package com.maelstrom.einar.order.domain.model;

import com.maelstrom.einar.order.application.PlacedOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class PrepareOrder
{

	private static final Logger log = LoggerFactory.getLogger(PrepareOrder.class);

	@TransactionalEventListener
	public void handle(NewOrderEvent event)
	{
		var order = ((PlacedOrder) event.getSource());
		log.info("acct-{} | Preparing order with id {} for customer: {}", order.accountId(), order.id(), order.customerId());

	}
}
