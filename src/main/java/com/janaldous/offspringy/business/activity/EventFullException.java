package com.janaldous.offspringy.business.activity;

@SuppressWarnings("serial")
public class EventFullException extends Exception {
	
	public EventFullException() {
		super("Event is full");
	}
	
}
