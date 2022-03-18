package com.yash.test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.yash.entity.Account;
import com.yash.factory.MySqlSessionfactory;
import com.yash.factory.OracleSessionFactory;
import com.yash.util.DataSeeder;

public class Test {

	public static void main(String[] args) {
		SessionFactory oracleSessionFactory = null;
		SessionFactory mysqlSessionFactory = null;
		Session oracleSession = null;
		Session mysqlSession = null;
		Transaction oracleTx = null;
		Transaction mysqlTx = null;
		try {
			mysqlSessionFactory = MySqlSessionfactory.getMySqlSessionFactory();
			mysqlSession = mysqlSessionFactory.openSession();
			mysqlTx = mysqlSession.beginTransaction();
			oracleSessionFactory = OracleSessionFactory.getOracleSessionFactory();
			oracleSession = oracleSessionFactory.openSession();
			oracleTx = oracleSession.beginTransaction();
			new DataSeeder("oracle");
			new DataSeeder("mysql");
			// get 5000 from oracle and update in mysql
			Account oracleAcc = (Account) oracleSession.get(Account.class, "12AE678");
			if(oracleAcc != null) {
				oracleAcc.setAccBal(oracleAcc.getAccBal() - 5000);
				oracleAcc.setAction("update");
				oracleAcc.settUpd(Timestamp.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))));
			}
			String oracleAccPk = (String) oracleSession.save(oracleAcc);
			Account mysqleAcc = (Account) mysqlSession.get(Account.class, "13AE987");
			if(mysqleAcc != null) {
				mysqleAcc.setAccBal(mysqleAcc.getAccBal() + 5000);
				mysqleAcc.setAction("update");
				mysqleAcc.settUpd(Timestamp.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))));
			}
			String mysqlAccPk = (String) mysqlSession.save(mysqleAcc);
			if (oracleAccPk.equals("12AE678") && mysqlAccPk.equals("13AE987")) {
				oracleTx.commit();
				mysqlTx.commit();
				System.out.println("Transaction Successfull");
			} else {
				System.out.println("Couldn' find these accounts");
			}
		} catch (Exception e) {
			e.printStackTrace();
			oracleTx.rollback();
			mysqlTx.rollback();
			System.out.println("Transaction Failed");
		} finally {
			if (mysqlSession != null)
				mysqlSession.close();
			if (oracleSession != null)
				oracleSession.close();
			if (oracleSessionFactory != null)
				oracleSessionFactory.close();
			if (mysqlSessionFactory != null)
				mysqlSessionFactory.close();
		}
	}

}
