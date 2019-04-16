package com.janaldous.offspringy.activity;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.activity.dto.EventPatchDto;
import com.janaldous.offspringy.entity.Event;

@Service
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Optional<Event> getEvent(Long eventId) {
		return eventRepository.findById(eventId);
	}

	@Override
	public Event add(@Valid Event event) {
		if (event.getId() != null) {
			throw new IllegalArgumentException("Event already exists");
		}
		
		return eventRepository.save(event);
	}

	@Override
	public Event update(@Valid Event event) {
		if (eventRepository.findById(event.getId()).isPresent()
				&& event.getAttendees().size() > 0) {
			throw new UpdateBookedEventException();
		}
		
		return eventRepository.save(event);
	}

	@Override
	public Event patch(Long eventId, EventPatchDto eventDto) {
		Event event = eventRepository.findById(eventId).get();
		modelMapper.typeMap(EventPatchDto.class, Event.class).setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(eventDto, event);
		return eventRepository.save(event);
	}

}
