package com.yash.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yash.entity.Account;
import com.yash.factory.MySqlSessionfactory;
import com.yash.factory.OracleSessionFactory;

public class DataSeeder {

	public DataSeeder(String db) {
		SessionFactory oracleSessionFactory = null;
		SessionFactory mysqlSessionFactory = null;
		Session oracleSession = null;
		Session mysqlSession = null;
		Transaction oracleTx = null;
		Transaction mysqlTx = null;
		try {
			if (db.equals("oracle")) {
				oracleSessionFactory = OracleSessionFactory.getOracleSessionFactory();
				oracleSession = oracleSessionFactory.openSession();
				oracleTx = oracleSession.beginTransaction();
				seedDatabase(oracleTx, oracleSession, "oracle");
			} else if (db.equals("mysql")) {
				mysqlSessionFactory = MySqlSessionfactory.getMySqlSessionFactory();
				mysqlSession = mysqlSessionFactory.openSession();
				mysqlTx = mysqlSession.beginTransaction();
				seedDatabase(mysqlTx, mysqlSession, "mysql");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't Seed");
		}
	}

	private static void seedDatabase(Transaction tx, Session session, String db) {
		try {
			System.out.println(db + "  " + tx + "    " + session);
			if (db.equals("oracle")) {
				String accNo = "12AE678";
				double accBal = 780000;
				for (int i = 0; i < 5; i++) {
					Account acc = new Account();
					acc.setAccNo(accNo);
					acc.setAccBal(accBal);
					acc.setAction("create");
					String pk = (String) session.save(acc);
					if (pk.equals(accNo)) {
//						tx.commit();
						System.out.println("Inserted into oracle");
					} else {
						System.out.println("Oracle insertion failed");
					}
					accNo = accNo.substring(0, 7)+(""+i);
//					System.out.println(accNo);
					accBal += Math.random();
				}
			} else if (db.equals("mysql")) {
				String accNo = "13AE987";
				double accBal = 720000;
				for (int i = 0; i < 5; i++) {
					Account acc = new Account();
					acc.setAccNo(accNo);
					acc.setAccBal(accBal);
					acc.setAction("create");
					String pk = (String) session.save(acc);
					if (pk.equals(accNo)) {
						System.out.println("Inserted into Mysql");
					} else {
						System.out.println("Mysql insertion failed");
					}
					accNo = accNo.substring(0, 7)+(""+i);
//					System.out.println(accNo);
					accBal += Math.random();
				}
			}
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			System.out.println("Transaction Failed");
		}
	}

}
