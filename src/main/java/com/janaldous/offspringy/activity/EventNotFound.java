package com.janaldous.offspringy.activity;

@SuppressWarnings("serial")
public class EventNotFound extends Exception {
	public EventNotFound() {
		super();
	}
	
	public EventNotFound(String message) {
		super(message);
	}
}
