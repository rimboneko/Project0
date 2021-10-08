package com.project.models;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int account_id;
	private double balance;
    private String username;
	
	public Account() {
		super();
	}
	
	public Account(int account_id ,double balance, String username) {
		
		this.account_id = account_id;
		this.balance = balance;
		this.username = username;
	}
	
	public Account(double balance, String username) {
		
		this.balance = balance;
		this.username = username;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", balance=" + balance + ", username=" + username + "]";
	}


}
