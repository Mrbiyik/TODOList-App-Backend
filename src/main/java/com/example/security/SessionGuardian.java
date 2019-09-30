package com.example.security;

import com.example.model.Token;

public interface SessionGuardian {
	
	
	public String createSession(String userid, String username);
	public Token controlSession(String token);
	public String sanitize(String input);
	public boolean nameControl(String input);
	public boolean usernameControl(String input);

}
