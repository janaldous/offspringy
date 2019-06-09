package com.janaldous.offspringy.business.booking.ticket;

import java.util.List;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.data.Ticket;
import com.janaldous.offspringy.business.customer.Customer;


public interface ITicket {

	boolean hasAvailableTickets(Long id) throws EventNotFoundException;
	
	void returnTicket(Long id, int numOfTickets) throws EventNotFoundException;

	List<Ticket> takeTicket(Customer customer, Event event, int numOfTickets) throws EventFullException;
	
}
