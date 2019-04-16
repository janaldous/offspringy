package com.janaldous.offspringy.activity;

@SuppressWarnings("serial")
public class ActivityDoesNotExistException extends Exception {
	public ActivityDoesNotExistException() {
		super();
	}
	
	public ActivityDoesNotExistException(String message) {
		super(message);
	}
}
