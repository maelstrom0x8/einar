package com.maelstrom.config.security;


import com.maelstrom.config.web.resolvers.AuthenticatedUserResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebSecurityConfiguration implements WebMvcConfigurer
{

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers)
	{
		resolvers.add(new AuthenticatedUserResolver());
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{

		http.csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable);
		http.sessionManagement(session ->
		{
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		});
		http.oauth2ResourceServer(server -> server.jwt(
			jwt -> jwt.jwtAuthenticationConverter(new DefaultJwtAuthenticationTokenConverter())));
		http.authorizeHttpRequests(requests ->
		{
			requests.requestMatchers("/actuator/**").permitAll();
			requests.anyRequest().authenticated();
		});

		return http.build();
	}

	@Bean
	public CorsFilter corsFilter()
	{
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://einar.com");
		config.addAllowedOrigin("https://einar.com");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowCredentials(true);


		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
