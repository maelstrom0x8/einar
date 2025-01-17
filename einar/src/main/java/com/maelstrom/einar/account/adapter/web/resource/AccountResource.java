package com.maelstrom.einar.account.adapter.web.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/accounts")
public class AccountResource
{

	@PostMapping("/register")
	public void registerAccount(@RequestBody String email) {}

}
