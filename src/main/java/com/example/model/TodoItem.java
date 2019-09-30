package com.example.model;

import java.sql.Date;

public class TodoItem {
	
	
	private long id;
	private long todoListid;
	private String name;
	private String status;
	private Date date;
	private Date deadline;
	
	public TodoItem(long id, long todoListid, String name, String status, Date date, Date deadline) {
		super();
		this.id = id;
		this.todoListid = todoListid;
		this.name = name;
		this.status = status;
		this.date = date;
		this.deadline = deadline;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTodoListid() {
		return todoListid;
	}
	public void setTodoListid(long todoListid) {
		this.todoListid = todoListid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

}
