package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.security.SessionGuardian;
import com.example.service.UserService;

@ComponentScan("com.example.config")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionGuardian guard;
	
	@CrossOrigin
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String register(String username, String password){
		
		username = guard.sanitize(username);
		password = guard.sanitize(password);
		if(userService.register(username, password) && guard.usernameControl(username) && guard.usernameControl(password))
			return "success";
		else
			return "failure";
	
	
    }
    @CrossOrigin
    @RequestMapping(value = "/login",method=RequestMethod.POST)
    	public String loginUser(@RequestParam(value="username")String username, @RequestParam(value="password") String password) {
			
    		User attacker = userService.login(username, password);
    		
    		if(attacker!= null) {
				
				return guard.createSession(""+attacker.getId(), attacker.getUsername());
				
			}else
				return "failure";
			
    }


}
