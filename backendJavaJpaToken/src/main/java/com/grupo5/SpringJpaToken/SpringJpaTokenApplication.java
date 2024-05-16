package com.grupo5.SpringJpaToken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"Firebase", "com.grupo5.SpringJpaToken"})

public class SpringJpaTokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaTokenApplication.class, args);
	}

}
