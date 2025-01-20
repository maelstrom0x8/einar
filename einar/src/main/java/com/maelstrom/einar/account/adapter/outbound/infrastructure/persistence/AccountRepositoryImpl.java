package com.maelstrom.einar.account.adapter.outbound.infrastructure.persistence;

import com.maelstrom.config.TenantContext;
import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;
import com.maelstrom.einar.account.domain.repository.AccountRepository;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.maelstrom.jooq.Tables.ACCOUNTS;

@Repository
class AccountRepositoryImpl implements AccountRepository
{
	private final DSLContext ctx;

	public AccountRepositoryImpl(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public Account save(Account account)
	{
		Integer id = TenantContext.getCurrentTenant();
		var accountsRecord = ctx.insertInto(ACCOUNTS)
			.set(ACCOUNTS.EMAIL, account.getEmail())
			.set(ACCOUNTS.NAME, account.getName())
			.set(ACCOUNTS.TENANT_ID, id)
			.returningResult(ACCOUNTS.ACCOUNT_ID, ACCOUNTS.CREATED_AT).fetchOne();

		if (accountsRecord == null)
		{
			return null;
		}
		account.setId(accountsRecord.getValue(ACCOUNTS.ACCOUNT_ID));
		account.setCreatedAt(accountsRecord.getValue(ACCOUNTS.CREATED_AT));
		return account;
	}

	@Override
	public void deleteById(AccountId accountId)
	{

	}

	@Override
	public Optional<Account> findById(AccountId accountId)
	{
		Optional<Record4<Integer, String, String, LocalDateTime>> _a = ctx.select(ACCOUNTS.ACCOUNT_ID, ACCOUNTS.EMAIL,
				ACCOUNTS.NAME, ACCOUNTS.CREATED_AT)
			.from(ACCOUNTS)
			.where(ACCOUNTS.ACCOUNT_ID.eq(accountId.id()).or(ACCOUNTS.EMAIL.eq(accountId.email())))
			.fetchOptional();

		if (_a.isPresent())
		{
			return _a.map(r ->
			{
				Account account = new Account(r.get(ACCOUNTS.NAME), r.get(ACCOUNTS.EMAIL));
				account.setId(r.get(ACCOUNTS.ACCOUNT_ID));
				account.setCreatedAt(r.get(ACCOUNTS.CREATED_AT));
				return account;
			});
		}

		return Optional.empty();
	}

	@Override
	public List<Account> findAll()
	{
		var _fetch = ctx.select(ACCOUNTS.ACCOUNT_ID, ACCOUNTS.EMAIL, ACCOUNTS.NAME, ACCOUNTS.CREATED_AT)
			.from(ACCOUNTS)
			.fetch();

		return _fetch.map(f ->
		{
			Account account = new Account(f.get(ACCOUNTS.NAME), f.get(ACCOUNTS.EMAIL));
			account.setId(f.get(ACCOUNTS.ACCOUNT_ID));
			account.setCreatedAt(f.get(ACCOUNTS.CREATED_AT));
			return account;
		});
	}


	@Override
	public void deleteAccountById(AccountId accountId)
	{
		ctx.deleteFrom(ACCOUNTS)
			.where(ACCOUNTS.ACCOUNT_ID.eq(accountId.id()).or(ACCOUNTS.EMAIL.eq(accountId.email())))
			.execute();
	}

	@Override
	public boolean existsById(AccountId accountId)
	{
		return ctx.fetchExists(ctx.selectFrom(ACCOUNTS)
			.where(ACCOUNTS.ACCOUNT_ID.eq(accountId.id()).or(ACCOUNTS.EMAIL.eq(accountId.email()))));
	}
}
