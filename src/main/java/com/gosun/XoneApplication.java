package com.gosun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class XoneApplication
{

	public static void main(String[] args) {
		SpringApplication.run(XoneApplication.class, args);
	}
}
