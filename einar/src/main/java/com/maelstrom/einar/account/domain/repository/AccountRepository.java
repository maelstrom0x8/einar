package com.maelstrom.einar.account.domain.repository;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;

import java.util.Optional;

public interface AccountRepository
{
	Account save(Account account);

	Optional<Account> findById(AccountId accountId);

	void deleteAccountById(AccountId accountId);

	boolean existsById(AccountId accountId);
}
