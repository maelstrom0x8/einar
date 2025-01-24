package com.maelstrom.einar.customer.domain.model;

public class CustomerDetails
{
	private String name;
	private Contact contact;

	private CustomerDetails() {}

	public CustomerDetails(String name, Contact contact)
	{
		this.name = name;
		this.contact = contact;
	}

	public static CustomerDetails empty()
	{
		return new CustomerDetails();
	}

	public String getName()
	{
		return name;
	}

	public Contact getContact()
	{
		return contact;
	}
}
