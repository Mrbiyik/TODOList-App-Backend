package com.example.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TodoList;
import com.example.model.Token;
import com.example.security.SessionGuardian;
import com.example.service.TodoListService;

@ComponentScan("com.example.config")
@RestController
public class TodoListController {
	
	@Autowired
	private TodoListService todoListService;
	@Autowired
	private SessionGuardian guard;
	
	@CrossOrigin
    @RequestMapping(value="/todolists", method=RequestMethod.POST)
    public List<TodoList> sendTodoLists(@RequestParam(value="token")String token){
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null) {
			System.out.println("ControlSession testi!--INFO-->Gönderilen ile Oluşturulan token'lar eşleşirse payload döner.");
			return todoListService.getTodoLists(Long.parseLong(userToken.getUserid()));
			
		}else {
			return null;
		}

	
    }
	
	@CrossOrigin
    @RequestMapping(value="/addtodolist", method=RequestMethod.POST)
    public String addList(@RequestParam(value="token")String token, @RequestParam(value="name")String name, @RequestParam(value="date") Date date){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null && guard.nameControl(name)) {
			
			name = guard.sanitize(name);
			
			String addedListId = todoListService.addTodoList(Long.parseLong(userToken.getUserid()), name, date);
			
			return addedListId;
			
			
		}else {
			return "failure";
		}
		
		
		
	
	
    }
	
	@CrossOrigin
    @RequestMapping(value="/deletetodolist", method=RequestMethod.POST)
    public String deleteList(@RequestParam(value="token")String token, @RequestParam(value="id")Long todolistid){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null) {
			
			boolean isDeleted = todoListService.deleteTodoList(todolistid);
			
			if(isDeleted)
				return "success";
			else
				return "failure";
			
			
		}else {
			return "failure";
		}
		
		
		
	
	
    }
	
	@CrossOrigin
    @RequestMapping(value="/additem", method=RequestMethod.POST)
    public String addItem(@RequestParam(value="token")String token, @RequestParam(value="listid")Long todolistid,@RequestParam(value="name")String itemname, @RequestParam(value="deadline")Date deadline){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null&&guard.nameControl(itemname)) {
			
			itemname = guard.sanitize(itemname);
			
			return todoListService.addItem(todolistid, deadline, itemname);
			
			
		}else {
			System.out.println("Karakter uzunluğu testi!");
			return "failure";
		}
		
		
		
	
	
    }
	
	
	@CrossOrigin
    @RequestMapping(value="/deleteitem", method=RequestMethod.POST)
    public String deleteItem(@RequestParam(value="token")String token, @RequestParam(value="itemid")Long itemid){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null) {
			
			boolean isDeleted = todoListService.deleteItem(itemid);
			
			if(isDeleted)
				return "success";
			else
				return "failure";
			
			
		}else {
			return "failure";
		}
		
		
		
	
	
    }
	
	
	@CrossOrigin
    @RequestMapping(value="/completeitem", method=RequestMethod.POST)
    public String completeItem(@RequestParam(value="token")String token, @RequestParam(value="itemid")Long itemid){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null) {
			
			boolean isDeleted = todoListService.completeItem(itemid);
			
			if(isDeleted)
				return "success";
			else
				return "failure";
			
			
		}else {
			return "failure";
		}

    }
	
	
	
	@CrossOrigin
    @RequestMapping(value="/adddependency", method=RequestMethod.POST)
    public String completeItem(@RequestParam(value="token")String token, @RequestParam(value="item")Long item, @RequestParam(value="condition")Long condition){
		
		
		
		Token userToken = guard.controlSession(token);
		
		if(userToken != null) {
			
			boolean isAdded = todoListService.addPrecondition(item, condition);
			
			if(isAdded)
				return "success";
			else
				return "failure";
			
			
		}else {
			return "failure";
		}

    }

}
