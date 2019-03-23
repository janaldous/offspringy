package com.janaldous.offspringy.activity;

import java.util.Optional;

import javax.validation.Valid;

import com.janaldous.offspringy.model.Event;

public interface IEventService {

	Optional<Event> findById(Long eventId);

	Event save(@Valid Event event);

}
