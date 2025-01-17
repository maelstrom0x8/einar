package com.maelstrom.einar;

import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests
{

	@Test
	void contextLoads()
	{
		ApplicationModules.of(Application.class, JavaClass.Predicates.resideInAPackage("com.maelstrom.einar"))
						.verify();
	}
}
