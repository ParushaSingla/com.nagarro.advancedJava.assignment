package com.nagarro.assignment.services.implementation;

import com.nagarro.assignment.dao.pojo.User;
import com.nagarro.assignment.dao.pojo.implementation.BaseDaoImplement;
import com.nagarro.assignment.services.BaseServices;

public class BaseServicesImplementation implements BaseServices {

	@Override
	public User login(String username, String password) {
		BaseDaoImplement loginDao = new BaseDaoImplement();
		return loginDao.login(username, password);
	}

}