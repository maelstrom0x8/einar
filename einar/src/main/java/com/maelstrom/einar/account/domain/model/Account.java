package com.maelstrom.einar.account.domain.model;

import java.time.LocalDateTime;

public class Account
{
	private Integer id;
	private String name;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastModified;

	public Account() {}

	public Account(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
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

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified)
	{
		this.lastModified = lastModified;
	}
}
