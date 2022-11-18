package com.springframework.springdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.springframework.springdemo")
public class AppConfig {
//	
//	@Bean
//	public Samsung getPhone() {
//		return new Samsung();
//	}
//
//	@Bean
//	public Processor getCPU() {
//		return new SnapDragon();
//	}
}
