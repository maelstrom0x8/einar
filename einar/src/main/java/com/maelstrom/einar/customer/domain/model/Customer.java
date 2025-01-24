package com.maelstrom.einar.customer.domain.model;

import java.time.LocalDateTime;

public class Customer
{
	private CustomerId id;
	private CustomerDetails details;
	private LocalDateTime createdAt;


	public Customer(CustomerDetails details) {
		this.details = details;
	}

	public void setCustomerId(CustomerId customerId) {
		this.id = customerId;
	}


	public void setId(CustomerId customerId)
	{
		this.id = customerId;
	}

	public CustomerId getId()
	{
		return id;
	}

	public CustomerDetails getDetails()
	{
		return details;
	}

	public void updateDetails(CustomerDetails details)
	{
		this.details = details;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}
}
