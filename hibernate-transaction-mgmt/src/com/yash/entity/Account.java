package com.yash.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Account")
public class Account {
	

	@Id
	@Column(name="ACCOUNT_NUM", length=10)
	private String accNo;
	
	@Column(name="ACCOUNT_BAL", length=6)
	private double accBal;
	
	@Column(name="ACTION", length=6)
	private String action;
	
	@Column(name="UPD_TMSTMP", length = 18)
	private Timestamp tUpd;
	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Timestamp gettUpd() {
		return tUpd;
	}
	public void settUpd(Timestamp tUpd) {
		this.tUpd = tUpd;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public double getAccBal() {
		return accBal;
	}
	public void setAccBal(double accBal) {
		this.accBal = accBal;
	}
	
}
