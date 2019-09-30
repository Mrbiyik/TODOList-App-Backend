package com.example.dao;

import java.sql.Date;
import java.util.List;

import com.example.model.TodoItem;
import com.example.model.TodoList;

public interface TodoListDao {
	
	
	public List<TodoList> getTodoLists(long userid);
	public String addTodoList(long userid, String listName, Date date);
	public boolean deleteTodoList(long todolistid);
	public String addItem(long todolistid, Date deadline, String name);
	public boolean deleteItem(long itemid);
	public boolean completeItem(long itemid);
	public boolean addPrecondition(long itemid, long precondition);
	public boolean checkPrecondition(long itemid, long precondition);
	public List<TodoItem> getItems(long todolistid);
	public boolean updateStatus(Long itemid, String status);
	public boolean checkPreCondition(Long itemid);
	public boolean deleteDependency(Long itemid);
	

}
