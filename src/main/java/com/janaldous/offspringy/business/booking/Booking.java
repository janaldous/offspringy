package com.janaldous.offspringy.business.booking;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.booking.data.Ticket;
import com.janaldous.offspringy.business.customer.Customer;
import com.janaldous.offspringy.util.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Booking extends Entity {
	private boolean confirmed;
	private boolean cancelled;
	private Customer customer;
	private Event event;
	private List<Ticket> tickets;
}
