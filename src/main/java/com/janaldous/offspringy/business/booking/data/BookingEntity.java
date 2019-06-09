package com.janaldous.offspringy.business.booking.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.customer.data.CustomerEntity;

@Data
@Entity
public class BookingEntity {
	@Id
	@GeneratedValue
	private Long id;
	private boolean confirmed;
	private boolean cancelled;
	@ManyToOne
	private CustomerEntity customer;
	@ManyToOne
	private Event event;
	@ManyToMany
	private List<Ticket> tickets;
}
