package com.maelstrom.config.security;

import com.maelstrom.einar.account.domain.AccountNotFoundException;
import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationService
{

	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	private final AccountRepository accountRepository;

	public AuthenticationService(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}

	public Account authenticate(AccountId accountId)
	{
		if (accountId == null || accountId.id() == null || accountId.email() == null)
		{
			throw new IllegalArgumentException("AccountId must not be null");
		}

		Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

		log.info("Found account with id {}", account.getId());

		return account;
	}
}
