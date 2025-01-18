package com.maelstrom.einar.account.adapter.inbound.web.resource;

import com.maelstrom.einar.account.adapter.inbound.web.dto.CreateAccountRequest;
import com.maelstrom.einar.account.application.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountResource
{

	private static final Logger log = LoggerFactory.getLogger(AccountResource.class);
	private final AccountService accountService;

	public AccountResource(AccountService accountService)
	{
		this.accountService = accountService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerAccount(@RequestBody CreateAccountRequest request)
	{
		log.info("Creating account for user {}", request.email());
		accountService.createAccount(request.name(), request.email());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
