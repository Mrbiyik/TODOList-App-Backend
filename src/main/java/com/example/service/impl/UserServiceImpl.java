package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean register(String username, String password) {
		//filtering methods!
		return userDao.register(username, password);
	}

	@Override
	public User login(String username, String password) {
		
		return userDao.login(username, password);
	}




}
