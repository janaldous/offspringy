package com.janaldous.offspringy.business.customer.data;

import java.util.Optional;

import com.janaldous.offspringy.business.customer.Customer;

public interface ICustomerRepositoryAdapter {

	Customer save(Customer activity);

	Customer update(Customer activity);

	void delete(Long id);

	Customer find(Long id);
	
	Optional<Customer> findByUserId(Long userId);
	
}
