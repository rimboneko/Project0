package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.models.Account;
import com.project.models.User;
import com.project.utils.ConnectionUtil;

public class AccountDaoDB implements AccountDao {
	
	ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();

	@Override
	public void createAccount(User u) throws SQLException {
		
		
		Connection con = conUtil.getConnection();
		String sql = "INSERT INTO accounts(balance, username) values"
				+ "(?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDouble(1, 0.0);
		ps.setString(2, u.getUsername());
		ps.execute();
		
	}

	@Override
	public List<Account> getAllAccount() {
		
		List<Account> accList = new ArrayList<Account>();
		
		try {
			
		Connection con = conUtil.getConnection();
		String sql = "SELECT * FROM accounts";
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);
			
		while(rs.next()) {
				
			accList.add(new Account(rs.getInt(1), rs.getDouble(2), rs.getString(3)));
		}
			
		return accList;	
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account getAccountByusername(String username) {
		
		Account acc = new Account();
		
		try {
			Connection con = conUtil.getConnection();
			String sql = "SELECT * FROM users WHERE users.username = '" + username + "'";
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				acc.setAccount_id(rs.getInt(1));
				acc.setBalance(rs.getDouble(2));
				acc.setUsername(rs.getString(3));
			}
			
			return acc;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void withdraw(double amount, String username) throws SQLException{
		
		Connection con = conUtil.getConnection();
		String sql = "UPDATE accounts SET balance = balance - ?" + "WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDouble(1, amount);
		ps.setString(2, username);
		ps.execute();
	}

	@Override
	public void deposit(double amount, String username) throws SQLException {
		
		Connection con = conUtil.getConnection();
		String sql = "UPDATE accounts SET balance = balance + ?" + "WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setDouble(1, amount);
		ps.setString(2, username);
		ps.execute();
		
	}

	@Override
	public void transfert(double amount, String fusername, String tusername) throws SQLException {
		
		this.withdraw(amount, fusername);
		this.deposit(amount, tusername);
	}

	@Override
	public double getBalance(String username) throws SQLException {
		double balance = 0;
		Connection con = conUtil.getConnection();
		String sql = "SELECT balance FROM accounts WHERE username = '" + username + "'";
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while(rs.next()) {
		 balance = rs.getDouble(1);
		}
		return balance;
	}

}
