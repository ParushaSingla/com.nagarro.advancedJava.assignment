package com.nagarro.assignment.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.nagarro.assignment.dao.pojo.Image;
import com.nagarro.assignment.dao.pojo.User;

public class hibernateutil {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	static {
		configuration = new Configuration().configure("com/nagarro/assignment/config/hibernate.cfg.xml")
				.addAnnotatedClass(User.class).addAnnotatedClass(Image.class);

		sessionFactory = configuration.buildSessionFactory();
	}

	public static Session getSession() {
		Session session = null;
		if (sessionFactory != null) {
			session = sessionFactory.openSession();
		}
		return session;
	}
}