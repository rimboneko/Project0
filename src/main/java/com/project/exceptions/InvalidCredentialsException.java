package com.project.exceptions;

public class InvalidCredentialsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidCredentialsException() {
		super("User signed in with invalid credentials");
	}

}
