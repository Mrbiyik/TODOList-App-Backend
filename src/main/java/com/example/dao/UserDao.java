package com.example.dao;

import com.example.model.User;

public interface UserDao{
	
	public boolean register(String username,String password);
	public User login(String username, String password);

}
