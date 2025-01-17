package com.maelstrom.einar.account.application;

import com.maelstrom.einar.account.domain.model.Account;
import com.maelstrom.einar.account.domain.model.AccountId;

public interface AuthenticationService
{
	Account authenticate(AccountId accountId);
}
