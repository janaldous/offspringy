package com.janaldous.offspringy.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.business.activity.EventFullException;
import com.janaldous.offspringy.business.booking.BookingException;
import com.janaldous.offspringy.util.EntityNotFoundException;
import com.janaldous.offspringy.web.dto.BookingDto;
import com.janaldous.offspringy.web.dto.SuccessfulBookingDto;
import com.janaldous.offspringy.web.serviceadapters.BookingServiceAdapter;

@RestController
@RequestMapping("/api")
@Api(value="booking")
public class BookingController {
	
	@Autowired
	private BookingServiceAdapter bookingBusiness;
	
	@ApiOperation(value = "Book an event", response = SuccessfulBookingDto.class)
	@PostMapping("/book")
	public ResponseEntity<SuccessfulBookingDto> bookEvent(
			@RequestBody BookingDto bookingDto) throws EntityNotFoundException, BookingException, EventFullException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		SuccessfulBookingDto reponse = bookingBusiness.book(username, bookingDto.getEventId(), bookingDto.getQuantity());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(reponse);
	}
	
}
