package com.maelstrom.config.web.resolvers;

import com.maelstrom.config.TenantContext;
import com.maelstrom.config.security.AuthID;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver
{

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Jwt principal = (Jwt) authentication.getPrincipal();
		Integer tenantId = Integer.valueOf(Objects.requireNonNull((String) principal.getClaims().get("tenant_id"), "tenant_id is required"));
		Integer accountId = Integer.valueOf(Objects.requireNonNull((String) principal.getClaims().get("account_id"), "account_id is required"));

		TenantContext.setCurrentTenant(tenantId);
		return accountId;
	}
}
