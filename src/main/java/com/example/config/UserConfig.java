package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.dao.UserDao;
import com.example.dao.impl.UserDaoImpl;
import com.example.service.UserService;
import com.example.service.impl.UserServiceImpl;

@Configuration
public class UserConfig {

	@Bean
	public UserService convertUserService() {
		
		return new UserServiceImpl();
	}
	@Bean
	public UserDao convertUserDao() {
		return new UserDaoImpl();
	}
	
	
}
