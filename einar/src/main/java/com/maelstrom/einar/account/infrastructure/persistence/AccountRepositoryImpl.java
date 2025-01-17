package com.maelstrom.einar.account.infrastructure.persistence;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository
{
	private final DSLContext ctx;

	public AccountRepositoryImpl(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public Account save(Account account)
	{
		return null;
	}

	@Override
	public Account findById(AccountId accountId)
	{
		return null;
	}

	@Override
	public void deleteAccountById(AccountId accountId)
	{

	}
}
