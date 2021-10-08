package com.project.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsernameAlreadyExistsException(){
		super("This username already exists");
	}

}
