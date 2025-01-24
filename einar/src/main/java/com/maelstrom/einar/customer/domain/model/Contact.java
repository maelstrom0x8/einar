package com.maelstrom.einar.customer.domain.model;

public class Contact
{
	private String email;
	private String phone;

	private Contact() {}

	public Contact(String email, String phone)
	{
		this.email = email;
		this.phone = phone;
	}

	public static Contact empty()
	{
		return new Contact();
	}

	public String getEmail()
	{
		return email;
	}

	public String getPhone()
	{
		return phone;
	}

	@Override
	public String toString()
	{
		return String.format("{email:%s, phone:%s}", email, phone);
	}
}
