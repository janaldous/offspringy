package com.janaldous.offspringy.activity;

@SuppressWarnings("serial")
public class EventDoesNotExistException extends Exception {
	public EventDoesNotExistException() {
		super();
	}
	
	public EventDoesNotExistException(String message) {
		super(message);
	}
}
