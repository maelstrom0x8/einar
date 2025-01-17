package com.maelstrom.config.web;

import com.maelstrom.einar.account.domain.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<?> handleUserNotFound(AccountNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.header("Location", "http://api.einar.com/v1/accounts/register")
						.body("Account for this user does not exist. Please register.");
	}
}
