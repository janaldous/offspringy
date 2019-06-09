package com.janaldous.offspringy.business.booking.payment;

import com.janaldous.offspringy.business.customer.Customer;

public interface IPay {
	Payment pay(Order order, Customer customer);
}
