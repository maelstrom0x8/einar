package com.maelstrom.einar.account.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException
{

	public AccountNotFoundException()
	{
		super();
	}

	public AccountNotFoundException(String message)
	{
		super(message);
	}
}
