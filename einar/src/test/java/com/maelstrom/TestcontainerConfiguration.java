package com.maelstrom;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration(proxyBeanMethods = false)
public class TestcontainerConfiguration
{
	@ServiceConnection
	@Bean
	PostgreSQLContainer<?> postgreSQLContainer()
	{
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"));
	}
}
