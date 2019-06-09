package com.janaldous.offspringy.business.booking;

import com.janaldous.offspringy.util.InvalidEntityException;


@SuppressWarnings("serial")
class InvalidBookingException extends InvalidEntityException {

	public InvalidBookingException(String message) {
		super(message);
	}

}
