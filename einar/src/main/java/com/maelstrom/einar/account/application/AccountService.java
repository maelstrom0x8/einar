package com.maelstrom.einar.account.application;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService
{
	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository)
	{
		this.accountRepository = accountRepository;
	}

	public Account getAccountByEmail(String email) {
		return accountRepository.findById(new AccountId(null, email));
	}

}
