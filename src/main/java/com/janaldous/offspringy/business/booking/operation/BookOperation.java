package com.janaldous.offspringy.business.booking.operation;

import java.util.List;

import javax.transaction.Transactional;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.Booking;
import com.janaldous.offspringy.business.booking.IBookingBusiness;
import com.janaldous.offspringy.business.booking.data.Ticket;
import com.janaldous.offspringy.business.booking.ticket.ITicket;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.util.InvalidEntityException;

@Transactional
public class BookOperation implements BookCommand {
	
	private ITicket ticketBusiness;
	private IBookingBusiness bookingBusiness;
	
	private Customer customer;
	private Event event;
	private int numOfTickets;
	private Booking savedBooking;
	
	public BookOperation(ITicket ticketBusiness, IBookingBusiness bookingBusiness, Customer customer, 
			Event event, int numOfTickets) {
		if (numOfTickets <= 0) {
			throw new IllegalStateException("Cannot book 0 or less tickets");
		}
		
		this.ticketBusiness = ticketBusiness;
		this.bookingBusiness = bookingBusiness;
		this.customer = customer;
		this.event = event;
		this.numOfTickets = numOfTickets;
	}
	
	@Override
	public void execute() {
		try {
			List<Ticket> tickets = ticketBusiness.takeTicket(customer, event, numOfTickets);
			
			Booking booking = Booking.builder()
					.confirmed(false)
					.customer(customer)
					.event(event)
					.tickets(tickets)
					.build();
			
			savedBooking = bookingBusiness.create(booking);
		} catch (InvalidEntityException | EventFullException e) {
			throw new BookOperationException(e);
		}
	}
	
	public Booking getBooking() {
		return this.savedBooking;
	}
	
}
