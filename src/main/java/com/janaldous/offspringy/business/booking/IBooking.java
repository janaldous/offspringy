package com.janaldous.offspringy.business.booking;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.customer.Customer;

public interface IBooking {
	
	Booking book(Customer customer, Event event, int numOfTickets) throws BookingException, EventFullException;
	
	Booking get(Long id) throws BookingNotFoundException;
	
	void cancelBooking(Long bookingId) throws BookingNotFoundException, BookingNotCancellableException;
	
}
