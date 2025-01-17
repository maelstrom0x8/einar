package com.maelstrom.config.web.resolvers;

import com.maelstrom.config.security.AuthID;
import com.maelstrom.config.security.AuthenticatedUser;
import com.maelstrom.config.security.AuthenticationManager;
import com.maelstrom.einar.account.application.AccountService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver
{

	private final AuthenticationManager authenticationManager;
	private final AccountService accountService;

	public AuthenticatedUserResolver(AuthenticationManager authenticationManager, AccountService accountService)
	{
		this.authenticationManager = authenticationManager;
		this.accountService = accountService;
	}


	@Override
	public boolean supportsParameter(MethodParameter parameter)
	{
		return parameter.hasParameterAnnotation(AuthID.class)
						&& parameter.getParameterType().equals(Integer.class);
	}

	@Override
	public Object resolveArgument(
					MethodParameter parameter,
					ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest,
					WebDataBinderFactory binderFactory)
					throws Exception
	{
		AuthenticatedUser authenticated = authenticationManager.authenticated();
		return accountService.getAccountByEmail(authenticated.subject()).getId();
	}
}
