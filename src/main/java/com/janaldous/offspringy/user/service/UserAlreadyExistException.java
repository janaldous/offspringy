package com.janaldous.offspringy.user.service;

public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public UserAlreadyExistException(String message) {
		this.message = message;
	}

}
