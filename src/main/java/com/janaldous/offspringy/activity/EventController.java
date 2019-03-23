package com.janaldous.offspringy.activity;

import io.swagger.annotations.Api;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.model.Activity;
import com.janaldous.offspringy.model.Event;

@RestController
@RequestMapping("/api")
@Api(value="event")
public class EventController {
	
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
    private ActivityRepository activityRepository;
	
	@Autowired
	private EventRepository eventRepository;
    
	@GetMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<Event> getEvent(@PathVariable Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
		return event.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PutMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event, @PathVariable Long activityId) {
		log.info("Request to update event: {}", event);
		
		Optional<Activity> activity = activityRepository.findById(activityId);
		if (!activity.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Event result = eventRepository.save(event);
        return ResponseEntity.ok().body(result);
    }
	
	@PutMapping("/activity/{id}/event")
    ResponseEntity<Activity> addEventToActivity(@Valid @RequestBody Event event, @PathVariable Long id) {
        log.info("Request to add event: {}", event);
        Activity result = activityRepository.findById(id).get();
        result.getEvents().add(event);
        activityRepository.save(result);
        return ResponseEntity.ok().body(result);
    }
}
