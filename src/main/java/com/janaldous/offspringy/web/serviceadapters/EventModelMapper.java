package com.janaldous.offspringy.web.serviceadapters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.activity.data.entity.Event;
import com.janaldous.offspringy.web.dto.EventDto;

@Component
class EventModelMapper {
	
	@Autowired
	private static ModelMapper modelMapper;
	
	static EventDto convertToEventDto(Event event) {
		return modelMapper.map(event, EventDto.class);
	}
	
	static Event convertToEventEntity(EventDto eventDto) {
		return modelMapper.map(eventDto, Event.class);
	}
}