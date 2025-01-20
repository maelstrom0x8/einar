package com.maelstrom.config;


import com.maelstrom.einar.common.data.TenantIdentifierListener;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultVisitListenerProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class ApplicationConfiguration
{
	@Value("${spring.mail.host}")
	private String mailHost;

	@Value("${spring.mail.port}")
	private String mailPort;

	@Bean
	JavaMailSender mailSender()
	{
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(Integer.parseInt(mailPort));
		return mailSender;
	}

	@Bean
	DSLContext context(DataSource dataSource) throws SQLException
	{
		DefaultConfiguration configuration = new DefaultConfiguration();
		configuration.setSQLDialect(SQLDialect.POSTGRES);
		configuration.setConnection(dataSource.getConnection());
		return DSL.using(configuration.derive(DefaultVisitListenerProvider.providers(
			new TenantIdentifierListener()
		)));
	}
}
