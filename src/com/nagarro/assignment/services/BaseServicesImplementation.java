package com.nagarro.assignment.services;

import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.daolayer.BaseDaoImplement;

public class BaseServicesImplementation implements BaseServices {

	@Override
	public User login(String username, String password) {
		BaseDaoImplement loginDao = new BaseDaoImplement();
		return loginDao.login(username, password);
	}

}