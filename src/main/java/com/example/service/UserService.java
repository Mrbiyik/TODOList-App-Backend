package com.example.service;

import com.example.model.User;

public interface UserService {
	
	public boolean register(String username, String password);
	public User login(String username, String password);
	

}
