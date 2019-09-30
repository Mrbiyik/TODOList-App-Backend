package com.example.huaweiAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.controller")
@SpringBootApplication
public class HuaweiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuaweiApiApplication.class, args);
	}

}
