package com.maelstrom.einar.customer.adapter.inbound.web.dto;

import com.maelstrom.einar.customer.domain.model.Customer;

public record CustomerResponse(Long id, String email, String phone, String name)
{
	public static CustomerResponse from(Customer customer)
	{
		return new CustomerResponse(customer.getId().value(), customer.getDetails().getContact().getEmail(),
			customer.getDetails().getContact().getPhone(),
			customer.getDetails().getName());
	}
}
