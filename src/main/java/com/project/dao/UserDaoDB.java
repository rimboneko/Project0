package com.project.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.project.logging.Logging;
import com.project.models.User;
import com.project.utils.ConnectionUtil;

public class UserDaoDB implements UserDao { 
		
		ConnectionUtil conUtil = ConnectionUtil.getConnectionUtil();
		
		@Override
		public List<User> getAllUsers() {
			
			List<User> userList = new ArrayList<User>();
			
			try {
				
				Connection con = conUtil.getConnection();
				String sql = "SELECT * FROM users";
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
		
				while(rs.next()) {
					userList.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
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
					user.setType(rs.getString(6));
				}
				
				return user;
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void createUser(User u) throws SQLException {
			
			Connection con = conUtil.getConnection();
			String sql = "INSERT INTO users(first_name, last_name, username, password, email, type) values"
					+ "(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setString(5, u.getEmail());
			ps.setString(6, u.getType());
			ps.execute();
			Logging.logger.info("User created");
		}

		@Override
		public void updateUser(User u) {
			
			try {
				Connection con = conUtil.getConnection();
				String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, type = ?"
						+ "WHERE users.username = ?";
				
				PreparedStatement ps = con.prepareStatement(sql);
				
				ps.setString(1, u.getFirstName());
				ps.setString(2, u.getLastName());
				ps.setString(3, u.getUsername());
				ps.setString(4, u.getPassword());
				ps.setString(5, u.getEmail());
				ps.setString(6, u.getType());
				ps.setString(7, u.getUsername());
				Logging.logger.info("User updated");
				
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
				Logging.logger.info("User deleted");
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			
		}

		@Override
		public void updateUserType(String username, String type) {
			try {
				Connection con = conUtil.getConnection();
				con.setAutoCommit(false);
				String sql = "call update_type(?,?)";
				CallableStatement cs = con.prepareCall(sql);
				
				cs.setString(1, username);
				cs.setString(2, type);
				
				cs.execute();
				
				con.setAutoCommit(true);
				Logging.logger.info("User updated");
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
		}

	}


