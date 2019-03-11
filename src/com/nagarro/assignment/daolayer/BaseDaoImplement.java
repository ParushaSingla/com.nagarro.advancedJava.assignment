package com.nagarro.assignment.daolayer;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.nagarro.assignment.dao.pojo.User;

import com.nagarro.assignment.util.hibernateutil;

public class BaseDaoImplement implements BaseDao {

	@Override
	public User login(String username, String password) {
		Session session = hibernateutil.getSession();
		if (session != null) {
			try {
				String sql = "from User where user_name=:username AND user_pwd=:password";
				Query<User> query = session.createQuery(sql, User.class);
				query.setParameter("username", username);
				query.setParameter("password",password);
				User result=query.uniqueResult();
				if (result!=null) {
					System.out.println("User: " + result.toString());
					return result;
				}
				else{
					System.out.println("Invalid");
					return null;
				}
			} catch (Exception exception) {
				System.out.println("Exception occred while reading user data: " + exception.getMessage());
				return null;
			}

		} else {
			System.out.println("DB server down.....");
		}
		return null;
	}

}