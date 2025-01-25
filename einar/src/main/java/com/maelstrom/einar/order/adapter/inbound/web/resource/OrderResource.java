package com.maelstrom.einar.order.adapter.inbound.web.resource;


import com.maelstrom.config.security.AuthID;
import com.maelstrom.einar.customer.application.CustomerNotFoundException;
import com.maelstrom.einar.order.adapter.inbound.web.dto.OrderRequest;
import com.maelstrom.einar.order.application.OrderService;
import com.maelstrom.einar.order.application.OrderSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderResource
{
	private static final Logger log = LoggerFactory.getLogger(OrderResource.class);
	private final OrderService orderService;

	public OrderResource(OrderService orderService)
	{
		this.orderService = orderService;
	}

	@PostMapping("/new")
	public ResponseEntity<?> placeOrder(@AuthID Integer accountId, @RequestBody OrderRequest request)
	{
		OrderSpecification spec = OrderSpecification.withCustomerId(request.customerId())
			.items(request.items())
			.scheduledFor(request.scheduleFor())
			.build();

		try
		{
			orderService.placeOrder(accountId, spec);
		} catch (Exception e)
		{
			if (e instanceof CustomerNotFoundException ex)
			{
				log.error("Failed to place order", ex);
				return ResponseEntity.badRequest().build();
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}


}
