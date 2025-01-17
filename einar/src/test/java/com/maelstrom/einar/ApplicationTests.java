package com.maelstrom.einar;

import com.maelstrom.common.AbstractIT;
import com.tngtech.archunit.core.domain.JavaClass;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ApplicationTests extends AbstractIT
{

	@Test
	void contextLoads()
	{
		ApplicationModules.of(Application.class, JavaClass.Predicates.resideInAPackage("com.maelstrom.einar"))
						.verify();
	}
}
