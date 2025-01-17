package com.maelstrom;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;

@Configuration(proxyBeanMethods = false)
public class TestcontainerConfiguration
{
	public static void init()
	{
		var mailhog = new GenericContainer<>("mailhog/mailhog")
			.withExposedPorts(1025, 8025);
		mailhog.start();
		System.setProperty("spring.mail.host", mailhog.getHost());
		System.setProperty("spring.mail.port", String.valueOf(mailhog.getMappedPort(1025)));
	}

	@Bean
	@ServiceConnection
	@SuppressWarnings("resource")
	PostgreSQLContainer<?> postgreSQLContainer() {
		return new PostgreSQLContainer<>("postgres:17-alpine")
			.withTmpFs(Collections.singletonMap("/test/tmpfs", "rw"));

	}

}
