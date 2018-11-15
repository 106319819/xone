package com.gosun.birip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BiripApplication
{

	public static void main(String[] args) {
		SpringApplication.run(BiripApplication.class, args);
	}
}
