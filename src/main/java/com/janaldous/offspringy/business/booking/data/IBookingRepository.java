package com.janaldous.offspringy.business.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<BookingEntity, Long> {

}
