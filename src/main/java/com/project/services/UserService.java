package com.project.services;

import java.sql.SQLException;

import com.project.dao.UserDao;
import com.project.exceptions.InvalidCredentialsException;
import com.project.exceptions.UserDoesNotExistException;
import com.project.exceptions.UsernameAlreadyExistsException;
import com.project.logging.Logging;
import com.project.models.User;

public class UserService {
	
private UserDao uDao;
	
	public UserService(UserDao u) {
		this.uDao = u;
	}
	
	public User signUp(String first, String last, String username, String password, String email, String type) throws UsernameAlreadyExistsException {
		
		User u = new User(first, last, username, password, email, type);
		
		try {
			uDao.createUser(u);
			Logging.logger.info("New user was registered");
		} catch (SQLException e) {
			Logging.logger.info("Username created that already exists in the database");
			throw new UsernameAlreadyExistsException();
		}
		
		return u;
	}
	
	public User signIn(String username, String password, String type) throws UserDoesNotExistException, InvalidCredentialsException{
		
	
		User u = uDao.getUserByUsername(username);
		
		if(!u.getUsername().equals(username)) {
			Logging.logger.info("User tried logging in that does not exist");
			throw new UserDoesNotExistException();
		}
		else if(!u.getPassword().equals(password)) {
			Logging.logger.info("User tried to login with invalid credentials");
			throw new InvalidCredentialsException();
		}
		else if(!u.getType().equals(type)){
			throw new InvalidCredentialsException();
		}
		else{
			Logging.logger.info("User was logged in");
			return u;
		}
		
		
	}
	
}
