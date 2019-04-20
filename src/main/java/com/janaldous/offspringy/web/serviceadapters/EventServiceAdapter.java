package com.janaldous.offspringy.web.serviceadapters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.activity.IEventService;
import com.janaldous.offspringy.web.dto.EventDto;
import com.janaldous.offspringy.web.dto.EventPatchDto;

@Component
public class EventServiceAdapter {

	@Autowired
	private IEventService eventService;

	public Optional<EventDto> getEvent(Long eventId) {
		return Optional.of(EventModelMapper.convertToEventDto(
				eventService.getEvent(eventId).get()));
	}

	public EventDto addEvent(EventDto event) {
		return EventModelMapper.convertToEventDto(
				eventService.add(EventModelMapper.convertToEventEntity(event)));
	}

	public EventDto patch(Long eventId, EventPatchDto event) {
		return EventModelMapper.convertToEventDto(eventService.patch(eventId, event));
	}
}