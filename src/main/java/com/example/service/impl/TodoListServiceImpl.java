package com.example.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.TodoListDao;
import com.example.model.TodoItem;
import com.example.model.TodoList;
import com.example.service.TodoListService;

public class TodoListServiceImpl implements TodoListService{

	@Autowired
	TodoListDao todoListDao;
	
	@Override
	public List<TodoList> getTodoLists(long userid) {
		
		Date today = new Date(System.currentTimeMillis());
		System.out.println("Today:"+today);
		
		List<TodoList> list = todoListDao.getTodoLists(userid);
		System.out.println("Todolists initialized");
		for(int i=0;i<list.size();i++){
			
			if(list.get(i).getItems()!=null) {
				
				
				for(int j=0;j<list.get(i).getItems().size();j++) {
				
					if(today.after(list.get(i).getItems().get(j).getDeadline()) && list.get(i).getItems().get(j).getStatus().equals("pending")) {
						boolean updated = updateStatus(list.get(i).getItems().get(j).getId(), "expired");
						
						if(updated) {
							System.out.println("getTodoLists methodu testi!--INFO-->Zamanı geçen ve pending durumundaki itemlerin durumlarını günceller.");
							list.get(i).getItems().get(j).setStatus("expired");
						}
					}
					
				}
				
			}
			
			
		}
		
		
		return list;
	}
	
	@Override
	public List<TodoItem> getItems(long todolistid) {
		// TODO Auto-generated method stub
		return todoListDao.getItems(todolistid);
	}

	@Override
	public String addTodoList(long userid, String listName, Date date) {
		
		return todoListDao.addTodoList(userid, listName, date);
	}

	@Override
	public boolean deleteTodoList(long todolistid) {
		// TODO Auto-generated method stub
		return todoListDao.deleteTodoList(todolistid);
	}


	@Override
	public boolean deleteItem(long itemid) {
		// TODO Auto-generated method stub
		return todoListDao.deleteItem(itemid);
	}

	@Override
	public boolean completeItem(long itemid) {
		
		if(checkPreCondition(itemid)) {
			
			return todoListDao.completeItem(itemid) && todoListDao.deleteDependency(itemid);
		}else {
			
			System.out.println("checkPreCondition methodu testi!--INFO-->Item koşulu varken tamamlanamaz.");
			return false;
		}
	}

	@Override
	public boolean updateStatus(Long itemid, String status) {
		// TODO Auto-generated method stub
		return todoListDao.updateStatus(itemid, status);
	}

	@Override
	public String addItem(long todolistid, Date deadline, String name) {
		
		Date today = new Date(System.currentTimeMillis());
		
		if(today.after(deadline))
			return "failure";
		else
			return todoListDao.addItem(todolistid, deadline, name);
		
	}

	@Override
	public boolean checkPreCondition(Long itemid) {
		// TODO Auto-generated method stub
		return todoListDao.checkPreCondition(itemid);
	}

	@Override
	public boolean deleteDependency(Long itemid) {
		// TODO Auto-generated method stub
		return todoListDao.deleteDependency(itemid);
	}

	@Override
	public boolean addPrecondition(long itemid, long precondition) {

		if(checkPrecondition(itemid, precondition)) {
			
			System.out.println("checkPrecondition methodu testi!--INFO-->Item daha önceden eşlendiği bir item ile tekrardan ilişki kuramaz.");
			
			if(todoListDao.addPrecondition(itemid, precondition))
				return true;
			else
				return false;
			
		}
		else
			return false;
	}

	@Override
	public boolean checkPrecondition(long itemid, long precondition) {
		// TODO Auto-generated method stub
		return todoListDao.checkPrecondition(itemid, precondition);
	}



}
