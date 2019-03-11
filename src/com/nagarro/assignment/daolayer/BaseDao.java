package com.nagarro.assignment.daolayer;

import com.nagarro.assignment.dao.pojo.User;

public interface BaseDao {
	 public User login(String username, String password);

	}
