package com.janaldous.offspringy.web.serviceadapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.activity.IEventBusiness;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.Booking;
import com.janaldous.offspringy.business.booking.BookingException;
import com.janaldous.offspringy.business.booking.IBooking;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.business.customer.ICustomerBusiness;
import com.janaldous.offspringy.user.IUserService;
import com.janaldous.offspringy.user.data.entity.User;
import com.janaldous.offspringy.util.EntityNotFoundException;
import com.janaldous.offspringy.web.dto.SuccessfulBookingDto;

@Component
public class BookingServiceAdapter {

	@Autowired
	private IBooking bookingService;
	
	@Autowired
	private ICustomerBusiness customerBusiness;
	
	@Autowired
	private IEventBusiness eventBusiness;
	
	@Autowired
	private IUserService userBusiness;
	
	@Autowired
	private ObjectMapper mapper;
	
	private SuccessfulBookingDto toBookingDto(Booking booking) {
		SuccessfulBookingDto response = new SuccessfulBookingDto();
		response.setQuantity(booking.getTickets().size());
		response.setEvent(EventServiceAdapter.convertToEventDto(booking.getEvent()));
		return response;
	}
	
	public SuccessfulBookingDto book(String username, Long eventId, int quantity) throws EntityNotFoundException, BookingException, EventFullException {
		User user = userBusiness.getUserByEmail(username).get();
		Customer customer = customerBusiness.findByUserId(user.getId());
		Event event = eventBusiness.getEvent(eventId);
		
		return toBookingDto(bookingService.book(customer, event, quantity));
	}

}
