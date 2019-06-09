package com.janaldous.offspringy.business.booking;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.IEventBusiness;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.operation.BookOperation;
import com.janaldous.offspringy.business.booking.ticket.ITicket;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.business.customer.ICustomerBusiness;

@Service
public class BookingApplication implements IBooking {
	
	@Autowired
	private ICustomerBusiness customerBusiness;
	
	@Autowired
	private IEventBusiness eventBusiness;
	
	@Autowired
	private ITicket ticketBusiness;
	
	@Autowired
	private IBookingBusiness bookingBusiness;
	
	@Override
	public Booking book(Customer customer, Event event, int numOfTickets) throws BookingException, EventFullException {
		if (!customerBusiness.exists(customer.getId()) || !eventBusiness.exists(event.getId())) {
			throw new BookingException("Customer or event is invalid");
		}

		BookOperation bookOperation = new BookOperation(ticketBusiness, bookingBusiness, customer, event, numOfTickets);
		bookOperation.execute();
		
		return bookOperation.getBooking();
	}

	@Override
	public void cancelBooking(Long bookingId) throws BookingNotFoundException, BookingNotCancellableException {
		Booking booking = null;
		try {
			booking = bookingBusiness.read(bookingId);
			ticketBusiness.returnTicket(booking.getEvent().getId(), booking.getTickets().size());
		} catch (EntityNotFoundException | com.janaldous.offspringy.util.EntityNotFoundException e) {
			throw new BookingNotFoundException();
		}
		
		if (booking != null && booking.getEvent().isCancellable()) {
			bookingBusiness.delete(bookingId);
		} else {
			throw new BookingNotCancellableException();
		}
	}

	@Override
	public Booking get(Long id) throws BookingNotFoundException {
		try {
			return bookingBusiness.read(id);
		} catch (EntityNotFoundException | com.janaldous.offspringy.util.EntityNotFoundException e) {
			throw new BookingNotFoundException();
		}
	}

}
