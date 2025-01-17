package com.maelstrom.einar.account.domain.repository;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;

public interface AccountRepository
{
	Account save(Account account);

	Account findById(AccountId accountId);

	void deleteAccountById(AccountId accountId);
}
