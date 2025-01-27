package com.maelstrom.einar.supplier.domain.model;

import com.maelstrom.einar.customer.domain.model.Contact;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Supplier
{

	private SupplierId id;

	private String name;
	private Contact contact;
	private boolean active;
	private final Set<String> items = new HashSet<>();
	private Integer accountId;

	public Supplier(String name,  Contact contact, Collection<String> items)
	{
		this.name = name;
		this.contact = contact;
		this.items.addAll(items);
	}

	public Set<String> getItems()
	{
		return items;
	}

	public void setAccountId(Integer accountId)
	{
		this.accountId = accountId;
	}

	public Integer getAccountId()
	{
		return accountId;
	}

	public SupplierId getId()
	{
		return id;
	}

	public void setId(SupplierId id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Contact getContact()
	{
		return contact;
	}

	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}
}
