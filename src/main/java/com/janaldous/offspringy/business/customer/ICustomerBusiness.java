package com.janaldous.offspringy.business.customer;

import com.janaldous.offspringy.util.EntityNotFoundException;
import com.janaldous.offspringy.util.IEntityBusiness;


public interface ICustomerBusiness extends IEntityBusiness<Customer> {

	Customer findByUserId(Long userId) throws EntityNotFoundException;

}
