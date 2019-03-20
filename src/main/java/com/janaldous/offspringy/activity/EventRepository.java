package com.janaldous.offspringy.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
