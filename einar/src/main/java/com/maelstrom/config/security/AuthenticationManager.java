package com.maelstrom.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationManager
{

	public AuthenticatedUser authenticated()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Jwt principal = (Jwt) authentication.getPrincipal();

		Map<String, Object> claims = principal.getClaims();
		return new AuthenticatedUser(principal.getSubject(), claims);
	}
}
