package com.janaldous.offspringy.activity;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.model.Event;

@Service
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Override
	public Optional<Event> findById(Long eventId) {
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

}
