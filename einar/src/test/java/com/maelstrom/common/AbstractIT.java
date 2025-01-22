package com.maelstrom.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maelstrom.TestcontainerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = "spring.security.oauth2.resourceserver.jwt.issuer-uri=test"
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainerConfiguration.class)
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.maelstrom")
public abstract class AbstractIT
{

	static {
		TestcontainerConfiguration.init();
	}

	@Autowired
	protected MockMvc mvc;

	@Autowired
	protected ObjectMapper objectMapper;
}
