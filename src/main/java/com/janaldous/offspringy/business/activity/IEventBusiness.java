package com.janaldous.offspringy.business.activity;

import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.web.dto.EventPatchDto;

public interface IEventBusiness {

	Event getEvent(Long eventId) throws EventNotFoundException;

//	Event add(Event event);
	
	Event update(Event event);
	
	Event patch(Long eventId, EventPatchDto event);

	boolean exists(Long id);
}
