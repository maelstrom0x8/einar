package com.maelstrom.einar.account.domain.repository;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.common.data.GenericRepository;

import java.util.Optional;

public interface AccountRepository extends GenericRepository<Account, AccountId>
{
	Account save(Account account);

	Optional<Account> findById(AccountId accountId);

	void deleteAccountById(AccountId accountId);

	boolean existsById(AccountId accountId);
}
