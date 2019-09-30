package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.dao.TodoListDao;
import com.example.dao.impl.TodoListDaoImpl;
import com.example.service.TodoListService;
import com.example.service.impl.TodoListServiceImpl;

@Configuration
public class TodoListConfig {

	@Bean
	public TodoListService convertTodoListService() {
		
		return new TodoListServiceImpl();
	}
	@Bean
	public TodoListDao convertTodoListDao() {
		return new TodoListDaoImpl();
	}
}
