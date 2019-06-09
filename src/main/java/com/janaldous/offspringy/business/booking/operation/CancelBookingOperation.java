package com.janaldous.offspringy.business.booking.operation;

import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.Booking;
import com.janaldous.offspringy.business.booking.BookingException;
import com.janaldous.offspringy.business.booking.BookingNotFoundException;
import com.janaldous.offspringy.business.booking.IBookingBusiness;
import com.janaldous.offspringy.business.booking.ticket.ITicket;


public class CancelBookingOperation implements BookCommand {
	
	private ITicket ticketBusiness;
	private IBookingBusiness bookingBusiness;
	private Booking booking;
	
	public CancelBookingOperation(ITicket ticketBusiness, IBookingBusiness bookingBusiness, Booking booking) {
		this.ticketBusiness = ticketBusiness;
		this.bookingBusiness = bookingBusiness;
		this.booking = booking;
	}
	
	@Override
	public void execute() {
		Event event = booking.getEvent();
		
		if (!event.isCancellable()) {
			throw new BookOperationException("Event is not cancellable");
		}
		
		int numOfTickets = booking.getTickets().size();
		
		try {
			ticketBusiness.returnTicket(event.getId(), numOfTickets);
			bookingBusiness.cancel(booking);
		} catch (EventNotFoundException | BookingNotFoundException | BookingException e) {
			throw new BookOperationException(e);
		}
	}

}
