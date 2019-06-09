package com.janaldous.offspringy.business.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {

}
