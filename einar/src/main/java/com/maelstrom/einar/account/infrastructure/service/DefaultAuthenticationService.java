package com.maelstrom.einar.account.infrastructure.service;

import com.maelstrom.einar.account.application.AuthenticationService;
import com.maelstrom.einar.account.domain.AccountNotFoundException;
import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements AuthenticationService
{

	private static final Logger log = LoggerFactory.getLogger(DefaultAuthenticationService.class);
	private final AccountRepository accountRepository;

	public DefaultAuthenticationService(AccountRepository accountRepository) {this.accountRepository = accountRepository;}

	@Override
	public Account authenticate(AccountId accountId)
	{
		if (accountId == null || accountId.id() == null || accountId.email() == null) {
			throw new IllegalArgumentException("AccountId must not be null");
		}

		Account account = accountRepository.findById(accountId);
		if (account == null) {
			throw new AccountNotFoundException("Invalid account id");
		}

		log.info("Found account with id {}", account.getId());

		return account;
	}
}
