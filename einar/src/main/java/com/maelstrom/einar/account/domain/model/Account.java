package com.maelstrom.einar.account.domain.model;

import java.time.LocalDateTime;

public class Account
{
	private Long id;
	private String name;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime lastModified;

	public Account(String name, String email)
	{
		this.name = name;
		this.email = email;
	}

	public Long getId() {return id;}
}
