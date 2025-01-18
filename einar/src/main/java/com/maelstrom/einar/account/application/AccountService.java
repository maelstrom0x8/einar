package com.maelstrom.einar.account.application;

import com.maelstrom.einar.account.domain.AccountNotFoundException;
import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService
{
	private static final Logger log = LoggerFactory.getLogger(AccountService.class);
	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}

	public Account getAccountByEmail(String email)
	{
		return accountRepository.findById(new AccountId(null, email)).orElseThrow(AccountNotFoundException::new);
	}

	@Transactional
	public void createAccount(String name, String email)
	{
		Account account = new Account(name, email);
		accountRepository.save(account);
		log.info("Created account with id {}", account.getId());
	}

}
