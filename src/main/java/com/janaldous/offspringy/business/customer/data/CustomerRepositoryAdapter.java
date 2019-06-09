package com.janaldous.offspringy.business.customer.data;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.offspringy.business.customer.Customer;

@Component
public class CustomerRepositoryAdapter implements ICustomerRepositoryAdapter {

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private ICustomerRepository repository;
	
	private Customer toCustomer(CustomerEntity customerEntity) {
		return mapper.convertValue(customerEntity, Customer.class);
	}

	private CustomerEntity toCustomerEntity(Customer customer) {
		return mapper.convertValue(customer, CustomerEntity.class);
	}

	@Override
	public Customer save(Customer customer) {
		return toCustomer(repository.save(toCustomerEntity(customer)));
	}

	@Override
	public Customer update(Customer customer) {
		return toCustomer(repository.save(toCustomerEntity(customer)));
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Customer find(Long id) {
		return toCustomer(repository.findById(id).get());
	}

	@Override
	public Optional<Customer> findByUserId(Long userId) {
		return Optional.of(toCustomer(repository.findAll().stream()
				.filter(customer -> customer.getUser().getId() == userId)
				.findFirst().get()));
	}

}
