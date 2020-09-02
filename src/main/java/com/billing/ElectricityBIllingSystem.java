package com.billing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication

public class ElectricityBIllingSystem extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ElectricityBIllingSystem.class, args);
	}
	
}
