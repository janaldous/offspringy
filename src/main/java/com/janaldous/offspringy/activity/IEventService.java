package com.janaldous.offspringy.activity;

import java.util.Optional;

import com.janaldous.offspringy.activity.data.entity.Event;
import com.janaldous.offspringy.web.dto.EventPatchDto;

public interface IEventService {

	Optional<Event> getEvent(Long eventId);

	Event add(Event event);
	
	Event update(Event event);
	
	Event patch(Long eventId, EventPatchDto event);
}
