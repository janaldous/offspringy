package com.janaldous.offspringy.business.booking.operation;


@SuppressWarnings("serial")
public class BookOperationException extends RuntimeException {

	public BookOperationException(String message) {
		super(message);
	}

	public BookOperationException(Exception e) {
		super(e);
	}

}
