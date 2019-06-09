package com.janaldous.offspringy.business.customer.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.janaldous.offspringy.user.data.entity.User;

import lombok.Data;

@Data
@Entity
public class CustomerEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@ManyToOne
	private User user;
}
