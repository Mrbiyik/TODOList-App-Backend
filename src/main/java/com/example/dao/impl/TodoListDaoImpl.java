package com.example.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.dao.TodoListDao;
import com.example.model.TodoItem;
import com.example.model.TodoList;
import com.mysql.cj.xdevapi.Statement;
@Repository
public class TodoListDaoImpl implements TodoListDao{
    
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<TodoList> getTodoLists(long userid) {
		
		
		String sql = "SELECT * FROM todolist WHERE ownerid = ? ";
		List<TodoList> lists = jdbcTemplate.query(sql, new TodoListRowMapper(),userid);
		for(int i=0;i<lists.size();i++) {
			lists.get(i).setItems(getItems(lists.get(i).getId()));
		}
		return lists;
	}
	@Override
	public List<TodoItem> getItems(long todolistid) {
		String sql = "SELECT * FROM todoitems WHERE todolistid = ? ";
		List<TodoItem> items = jdbcTemplate.query(sql, new TodoItemRowMapper(),todolistid);
		return items;
	}

	@Override
	public String addTodoList(long userid, String listName, Date date) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO todolist (ownerid,name,date) VALUES(?,?,?)";
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, userid);
	          ps.setString(2, listName);
	          ps.setDate(3, date);
	          return ps;
	        }, keyHolder);
	 
	        return keyHolder.getKey().longValue()+"";
	    }
	

	@Override
	public boolean deleteTodoList(long listid) {
		
		String deleteListSQL = "DELETE FROM todolist WHERE id = ?";
		String deleteListItemsSQL = "DELETE FROM todoitems WHERE todolistid = ?";
		
		try {
			
			jdbcTemplate.update(deleteListSQL, listid);
			jdbcTemplate.update(deleteListItemsSQL, listid);
		
			return true;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
		
	}

	@Override
	public String addItem(long todolistid, Date deadline, String name) {
		
		String status = "pending";
		Date today = new Date(System.currentTimeMillis());
		String sql = "INSERT INTO todoitems (todolistid,name,status,createdate,deadline) VALUES(?,?,?,?,?)";
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    
	    jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
	          ps.setLong(1, todolistid);
	          ps.setString(2, name);
	          ps.setString(3, status);
	          ps.setDate(4, today);
	          ps.setDate(5, deadline);
	          return ps;
	        }, keyHolder);
	 
	        return keyHolder.getKey().longValue()+"";
	}

	@Override
	public boolean deleteItem(long itemid) {
		
		String deleteItemSQL = "DELETE FROM todoitems WHERE id = ?";
		String deleteLeftDependencySQL = "DELETE FROM dependency WHERE item = ?";
		String deleteRightDependencySQL = "DELETE FROM dependency WHERE precondition = ?";
		
		try {
			
			jdbcTemplate.update(deleteItemSQL, itemid);
			jdbcTemplate.update(deleteLeftDependencySQL, itemid);
			jdbcTemplate.update(deleteRightDependencySQL, itemid);
		
			return true;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
		
	}

	@Override
	public boolean completeItem(long itemid) {
		// TODO Auto-generated method stub
		
		String status = "completed";
		
		String updateItemSQL = "UPDATE todoitems SET status = ? WHERE id = ?";
		
		try {
			
			int isUpdated = jdbcTemplate.update(updateItemSQL, status, itemid);

			if(isUpdated == 1)
				return true;
			else
				return false;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
		
	}

	public class TodoListRowMapper implements RowMapper<TodoList> {
	    public TodoList mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	TodoList list = new TodoList(rs.getLong("id"),rs.getLong("ownerid"), rs.getString("name"), rs.getDate("date"));
	        return list;
	    }
	}
	public class TodoItemRowMapper implements RowMapper<TodoItem> {
	    public TodoItem mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	TodoItem item = new TodoItem(rs.getLong("id"),rs.getLong("todolistid"), rs.getString("name"), rs.getString("status"),rs.getDate("createdate"),rs.getDate("deadline"));
	        return item;
	    }
	}
	@Override
	public boolean updateStatus(Long itemid, String status) {
		
		String updateItemSQL = "UPDATE todoitems SET status = ? WHERE id = ?";
		
		try {
			
			int isUpdated = jdbcTemplate.update(updateItemSQL, status, itemid);
			
			System.out.println("Status update:"+isUpdated);
			
			if(isUpdated == 1)
				return true;
			else
				return false;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
		
	}
	@Override
	public boolean checkPreCondition(Long itemid) {

		String checkPrecondition = "SELECT count(*) FROM dependency WHERE item = ?";
		
		int numberOfPrecondition = jdbcTemplate.queryForObject(checkPrecondition, new Object[] { itemid }, Integer.class);
		
		if(numberOfPrecondition>0)
			return false;
		else
			return true;
	}
	@Override
	public boolean deleteDependency(Long itemid) {
		
		String deleteRightDependencySQL = "DELETE FROM dependency WHERE precondition = ?";
		
		try {
			
			jdbcTemplate.update(deleteRightDependencySQL, itemid);
		
			return true;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
	}
	@Override
	public boolean addPrecondition(long itemid, long precondition) {

		String sql = "INSERT INTO dependency (item,precondition) VALUES(?,?)";
		
		int rows = jdbcTemplate.update(sql, itemid,precondition);
		
		if(rows>0)
			return true;
		else
			return false;
	}
	@Override
	public boolean checkPrecondition(long itemid, long precondition) {
		
		String checkPrecondition = "SELECT count(*) FROM dependency WHERE item = ? AND precondition= ?";
		
		int numberOfPrecondition = jdbcTemplate.queryForObject(checkPrecondition, new Object[] { itemid, precondition}, Integer.class);
		
		System.out.println("pre:"+numberOfPrecondition);
		
		if(numberOfPrecondition>0)
			return false;
		else
			return true;
	}


}
