package com.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.models.User;
import com.project.utils.ConnectionUtil;

public class UserDaoDB implements UserDao { 
		
		ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
		
		//Use Statements to talk to our database
		@Override
		public List<User> getAllUsers() {
			
			List<User> userList = new ArrayList<User>();
			
			try {
				//Make the actual connection to the db
				Connection con = conUtil.getConnection();
				
				//Create a simple statement
				String sql = "SELECT * FROM users";
				
				//We need to create a statement with the sql string
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				//We have to loop through the ResultSet and create objects based off the return
				while(rs.next()) {
					userList.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
				}
				
				return userList;
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		public User getUserByUsername(String username) {
			
			User user = new User();
			
			try {
				Connection con = conUtil.getConnection();
				
				String sql = "SELECT * FROM users WHERE users.username = '" + username + "'";
				
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				while(rs.next()) {
					user.setFirstName(rs.getString(1));
					user.setLastName(rs.getString(2));
					user.setUsername(rs.getString(3));
					user.setPassword(rs.getString(4));
					user.setEmail(rs.getString(5));
				}
				
				return user;
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		//We use prepared statements to precompile the sql query and protect against SQL Injection
		
		@Override
		public void createUser(User u) throws SQLException {
			
			Connection con = conUtil.getConnection();
			
			//We will still create the sql string, but with some small changes
			String sql = "INSERT INTO users(first_name, last_name, username, password, email) values"
					+ "(?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getEmail());
			ps.execute();
			
		}

		@Override
		public void updateUser(User u) {
			
			try {
				Connection con = conUtil.getConnection();
				String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?"
						+ "WHERE users.username = ?";
				
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, u.getFirstName());
				ps.setString(2, u.getLastName());
				ps.setString(3, u.getUsername());
				ps.setString(4, u.getPassword());
				ps.setString(5, u.getEmail());
				ps.setString(6, u.getUsername());
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}

		@Override
		public void deleteUser(User u) {

			try {
				Connection con = conUtil.getConnection();
				String sql = "DELETE FROM users WHERE users.username = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, u.getUsername());
				ps.execute();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}

	}


