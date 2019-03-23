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
	public Event save(@Valid Event event) {
		return eventRepository.save(event);
	}

}
