package com.janaldous.offspringy.business.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.business.customer.data.ICustomerRepositoryAdapter;
import com.janaldous.offspringy.util.EntityNotFoundException;
import com.janaldous.offspringy.util.InvalidEntityException;

@Service
public class CustomerBusiness implements ICustomerBusiness {
	
	@Autowired
	private ICustomerRepositoryAdapter repository;
	
	@Override
	public Customer create(Customer activity) throws InvalidEntityException {
		return repository.save(activity);
	}
	
	@Override
	public Customer update(Customer activity) throws InvalidEntityException,
			EntityNotFoundException {
		return repository.update(activity);
	}

	@Override
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Customer read(Long id) throws EntityNotFoundException {
		return repository.find(id);
	}

	@Override
	public boolean exists(Long id) {
		return repository.find(id) != null;
	}

	@Override
	public Customer findByUserId(Long userId) throws EntityNotFoundException {
		return repository.findByUserId(userId).get();
	}

}
