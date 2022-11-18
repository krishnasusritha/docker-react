package com.springboot.cruddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		//SpringApplicationBuilder app = new SpringApplicationBuilder().properties(defaultProperties);
		SpringApplication.run(CruddemoApplication.class, args);
	}

}
