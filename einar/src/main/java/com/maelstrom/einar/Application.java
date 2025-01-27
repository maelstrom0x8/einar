package com.maelstrom.einar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;
import org.springframework.scheduling.annotation.EnableScheduling;

@Modulithic(systemName = "einar-business-core", useFullyQualifiedModuleNames = true)
@SpringBootApplication(scanBasePackages = "com.maelstrom")
@EnableScheduling
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}
