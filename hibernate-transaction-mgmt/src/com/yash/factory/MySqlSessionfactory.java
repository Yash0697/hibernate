package com.yash.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class MySqlSessionfactory {
private static SessionFactory sessionFactory = null;
	
	static {
		StandardServiceRegistry serviceRegistry;
		try {
			Configuration config = new Configuration();
			config.configure("/com/yash/resources/hibernate-mysql.cfg.xml");
			serviceRegistry = new StandardServiceRegistryBuilder()
									.applySettings(config.getProperties())
									.build();
			sessionFactory = config.buildSessionFactory(serviceRegistry);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getMySqlSessionFactory() {
		return sessionFactory;
	}
}
