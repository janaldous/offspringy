package com.janaldous.offspringy.business.activity;

@SuppressWarnings("serial")
public class ActivityNotFound extends Exception {
	public ActivityNotFound() {
		super();
	}
	
	public ActivityNotFound(String message) {
		super(message);
	}
}
