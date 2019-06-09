package com.janaldous.offspringy.business.booking.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.customer.data.CustomerEntity;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Ticket {
	
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private CustomerEntity customer;
	@ManyToOne
	private Event event;
	
}
