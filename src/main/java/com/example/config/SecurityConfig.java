package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.security.SessionGuardian;
import com.example.security.SessionGuardianImpl;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SessionGuardian guardTheAPI() {
		
		return new SessionGuardianImpl();
	}

}
