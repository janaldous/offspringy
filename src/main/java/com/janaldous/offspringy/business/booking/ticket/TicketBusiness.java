package com.janaldous.offspringy.business.booking.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.business.activity.IEventBusiness;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.data.ITicketRepository;
import com.janaldous.offspringy.business.booking.data.Ticket;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.business.customer.data.ICustomerRepository;
import com.janaldous.offspringy.util.EntityNotFoundException;

@Service
public class TicketBusiness implements ITicket {

	@Autowired
	private IEventBusiness eventBusiness;
	
	@Autowired
	private ITicketRepository ticketRepository;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Override
	public boolean hasAvailableTickets(Long id) throws EventNotFoundException {
		return getEvent(id).getCapacity() > 0; 
	}

	@Override
	public void returnTicket(Long id, int numOfTickets) throws EventNotFoundException {
		Event event = getEvent(id);
		int newCapacity = event.getCapacity() + numOfTickets;
		event.setCapacity(newCapacity);
		
		eventBusiness.update(event);
	}

	private Event getEvent(Long id) throws EventNotFoundException {
		try {
			return eventBusiness.getEvent(id);
		} catch (EntityNotFoundException e) {
			throw new EventNotFoundException();
		}
	}

	@Override
	public List<Ticket> takeTicket(Customer customer, Event event,
			int numOfTickets) throws EventFullException {
		if (event.getCapacity() - numOfTickets < 0) {
			throw new EventFullException();
		}
		int newCapacity = event.getCapacity() - numOfTickets;
		event.setCapacity(newCapacity);
		
		eventBusiness.update(event);
		
		List<Ticket> tickets = new ArrayList<>();
		for (int i = 0; i < numOfTickets; i++) {
			Ticket ticket = new Ticket();
			ticket.setCustomer(customerRepository.findById(customer.getId()).get());
			ticket.setEvent(event);
			ticket = ticketRepository.save(ticket);
			tickets.add(ticket);
		}
		
		return tickets;
	}

}
