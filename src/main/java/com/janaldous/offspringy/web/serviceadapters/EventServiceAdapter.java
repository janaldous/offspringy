package com.janaldous.offspringy.web.serviceadapters;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.business.activity.ActivityNotFound;
import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.business.activity.IActivityService;
import com.janaldous.offspringy.business.activity.IEventBusiness;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.dto.EventDto;
import com.janaldous.offspringy.web.dto.EventPatchDto;

@Component
public class EventServiceAdapter {

	@Autowired
	private IEventBusiness eventService;
	
	@Autowired
	private IActivityService activityService;
	
	private static ModelMapper modelMapper;
	
	@Autowired
	public EventServiceAdapter(ModelMapper modelMapper) {
		EventServiceAdapter.modelMapper = modelMapper;
	}

	public Optional<EventDto> get(Long eventId) throws EventNotFoundException {
		return Optional.of(convertToEventDto(
				eventService.getEvent(eventId)));
	}

	public EventDto patch(Long eventId, EventPatchDto event) {
		return convertToEventDto(eventService.patch(eventId, event));
	}
	
	public Collection<EventDto> getAll(Long activityId) throws ActivityNotFound {
		return activityService.getEvents(activityId)
				.stream()
				.map(EventServiceAdapter::convertToEventDto)
				.collect(Collectors.toList());
	}
	
	public ActivityDto addEvent(Long activityId, EventDto event) throws EventNotFoundException {
		return ActivityModelMapper.convertToDto(activityService.addEvent(activityId, convertToEventEntity(event)));
	}
	
	static EventDto convertToEventDto(Event event) {
		return modelMapper.map(event, EventDto.class);
	}
	
	private Event convertToEventEntity(EventDto eventDto) {
		return modelMapper.map(eventDto, Event.class);
	}
}