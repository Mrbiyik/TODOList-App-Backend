package com.example.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.dao.UserDao;
import com.example.model.User;


@Repository
public class UserDaoImpl implements UserDao{

	
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public boolean register(String username, String password) {
		String sql = "INSERT INTO users (username,password) VALUES(?,?) ";
		
		try {
			
		int inserted = jdbcTemplate.update(sql, username,password);
		
		if(inserted==1)
			return true;
		else
			return false;
		
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
	}

	@Override
	public User login(String username, String password) {
		String sql = "SELECT id, username FROM USERS WHERE username=? AND password=?";
		try {
				User user = jdbcTemplate.queryForObject(sql,new UserRowMapper(),username,password);
				return user;
		}catch (EmptyResultDataAccessException e) {
				return null;
		}
	}
	
	//User row mapper!
	public class UserRowMapper implements RowMapper<User> {
	    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	User user = new User(rs.getLong("id"),rs.getString("username"));
	        return user;
	    }
	}


}
