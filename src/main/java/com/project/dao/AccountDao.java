package com.project.dao;

import java.sql.SQLException;
import java.util.List;

import com.project.models.Account;
import com.project.models.Transactions;
import com.project.models.User;

public interface AccountDao {
	
	public void createAccount(User u) throws SQLException;
	
	public List<Account> getAllAccount();
	
	public Account getAccountByusername(String username);
	
	public void withdraw(double amount, String username) throws SQLException;
	
	public void deposit(double amount, String username) throws SQLException;
	
	public void transfert(double amount, String fusername, String tusername) throws SQLException;
	
	public double getBalance(String username) throws SQLException;
	
	public void setTransaction(String type, int acc, String status) throws SQLException;
	
	public List<Transactions> getTransactions();
	
	public void cancelAccount(int acc_id);
	

}
