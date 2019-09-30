package com.example.model;

import java.sql.Date;
import java.util.List;

public class TodoList {
	
	private long id;
	private long ownerid;
	private String name;
	private List<TodoItem> items;
	private Date date;
	
	public TodoList(long id, long ownerid, String name, Date date) {
		this.id = id;
		this.ownerid = ownerid;
		this.name = name;
		this.date = date;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(long ownerid) {
		this.ownerid = ownerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TodoItem> getItems() {
		return items;
	}

	public void setItems(List<TodoItem> items) {
		this.items = items;
	}
	
	
	

}
