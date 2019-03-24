package com.janaldous.offspringy.activity;

import java.util.Optional;

import javax.validation.Valid;

import com.janaldous.offspringy.entity.Event;

public interface IEventService {

	Optional<Event> findById(Long eventId);

	Event add(@Valid Event event);
	
	Event update(@Valid Event event);
	
}
