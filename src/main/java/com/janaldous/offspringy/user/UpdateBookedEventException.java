package com.janaldous.offspringy.user;

/**
 * Exception happens when a booked event, where at least one attendee has booked a ticket,
 * is updated. 
 * 
 * TODO In the future, attendees should be notified by email through a Spring Event and this exception is unnecessary
 * 
 * @author janaldoustorres
 *
 */
public class UpdateBookedEventException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public UpdateBookedEventException() {
		message = "Potential errors when updating an event which has booked attendees.";
	}

	public String getMessage() {
		return message;
	}
}
