package com.janaldous.offspringy.activity;

import java.util.Optional;

import com.janaldous.offspringy.entity.Event;

public interface IEventService {

	Optional<Event> getEvent(Long eventId);

	Event add(Event event);
	
	Event update(Event event);
	
}
