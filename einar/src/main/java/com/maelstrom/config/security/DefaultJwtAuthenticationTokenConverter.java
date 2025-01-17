package com.maelstrom.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class DefaultJwtAuthenticationTokenConverter
				implements Converter<Jwt, CustomJwtAuthenticationToken>
{

	@Override
	@SuppressWarnings("unchecked")
	public CustomJwtAuthenticationToken convert(Jwt source)
	{
		List<String> authorities = (List<String>) source.getClaims().get("authorities");
		return new CustomJwtAuthenticationToken(
						source, authorities.stream().map(SimpleGrantedAuthority::new).toList());
	}
}
