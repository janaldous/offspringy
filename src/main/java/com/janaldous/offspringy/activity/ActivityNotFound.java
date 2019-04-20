package com.janaldous.offspringy.activity;

@SuppressWarnings("serial")
public class ActivityNotFound extends Exception {
	public ActivityNotFound() {
		super();
	}
	
	public ActivityNotFound(String message) {
		super(message);
	}
}
