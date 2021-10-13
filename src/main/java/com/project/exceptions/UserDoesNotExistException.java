package com.project.exceptions;

public class UserDoesNotExistException extends Exception {
	
private static final long serialVersionUID = 1L;
	
	public UserDoesNotExistException() {
		super("User tried logging in with credentials that don't exist");
	}

}
