package com.janaldous.offspringy.business.booking;

import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.util.IEntityBusiness;

public interface IBookingBusiness extends IEntityBusiness<Booking> {
	
	Booking find(Customer customer, Event event);
	
	void cancel(Booking booking) throws BookingNotFoundException, BookingException;
	
}
