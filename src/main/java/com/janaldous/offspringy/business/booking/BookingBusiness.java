package com.janaldous.offspringy.business.booking;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.data.BookingEntity;
import com.janaldous.offspringy.business.booking.data.IBookingRepository;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.util.EntityNotFoundException;
import com.janaldous.offspringy.util.InvalidEntityException;

@Component
public class BookingBusiness implements IBookingBusiness {
	
	@Autowired
	private IBookingRepository repository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public Booking find(Customer customer, Event event) {
		return toBooking(repository.findAll().stream()
			.filter(booking -> booking.getEvent().equals(event) && booking.getCustomer().equals(customer))
			.collect(Collectors.toList())
			.get(0));
	}
	
	private Booking toBooking(BookingEntity bookingEntity) {
		return mapper.convertValue(bookingEntity, Booking.class);
	}
	
	private BookingEntity toBookingEntity(Booking booking) {
		if (booking == null) return null;
		return mapper.convertValue(booking, BookingEntity.class);
	}

	@Override
	public void cancel(Booking booking) throws BookingNotFoundException, BookingException {
		BookingEntity bookingEntity = toBookingEntity(booking);
		
		if (repository.findById(bookingEntity.getId()) == null) {
			throw new BookingNotFoundException();
		}
		
		if (bookingEntity.isCancelled()) {
			throw new BookingException("Booking is already cancelled");
		}
		
		bookingEntity.setCancelled(true);
		repository.save(bookingEntity);
	}

	@Override
	public Booking create(Booking booking) throws InvalidEntityException {
		return toBooking(repository.save(toBookingEntity(booking)));
	}

	@Override
	public Booking update(Booking booking) throws InvalidEntityException,
			EntityNotFoundException {
		return toBooking(repository.save(toBookingEntity(booking)));
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Booking read(Long id) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
