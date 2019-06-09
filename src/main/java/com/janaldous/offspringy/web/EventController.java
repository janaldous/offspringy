package com.janaldous.offspringy.web;

import io.swagger.annotations.Api;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.business.activity.ActivityNotFound;
import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.dto.EventDto;
import com.janaldous.offspringy.web.dto.EventPatchDto;
import com.janaldous.offspringy.web.serviceadapters.ActivityServiceAdapter;
import com.janaldous.offspringy.web.serviceadapters.EventServiceAdapter;

@RestController
@RequestMapping("/api")
@Api(value="event")
public class EventController {
	
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
    private ActivityServiceAdapter activityService;
	
	@Autowired
	private EventServiceAdapter eventService;
	
	@Autowired
	private ModelMapper modelMapper;
    
	@GetMapping("/activity/{activityId}/event")
	ResponseEntity<?> getEvents(@PathVariable Long activityId) {
		if (!activityService.findActivity(activityId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			Collection<EventDto> events = eventService.getAll(activityId);
			return ResponseEntity.ok().body(events);
		} catch (ActivityNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
	
	@GetMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) throws EventNotFoundException {
        Optional<EventDto> eventDto = eventService.get(eventId);

        return eventDto.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@PutMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<ActivityDto> putEvent(@Valid @RequestBody EventDto event, @PathVariable Long activityId) throws EventNotFoundException {
		log.info("Request to update event: {}", event);

		if (!activityService.findActivity(activityId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		ActivityDto result = eventService.addEvent(activityId, event);
        return ResponseEntity.ok().body(result);
    }
	
	@PutMapping("/activity/{activityId}/event")
    ResponseEntity<ActivityDto> addEventToActivity(@Valid @RequestBody EventDto event, @PathVariable Long activityId) throws EventNotFoundException {
        log.info("Request to add event: {}", event);
        
        ActivityDto result = eventService.addEvent(activityId, event);
        return ResponseEntity.ok().body(result);
    }
	
	@PatchMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<EventDto> patchEvent(@Valid @RequestBody EventPatchDto event, 
    		@PathVariable Long activityId,
    		@PathVariable Long eventId) throws EventNotFoundException {
		log.info("Request to update event: {}", event);
		
		if (!activityService.findActivity(activityId).isPresent() 
				|| !eventService.get(eventId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		EventDto result = eventService.patch(eventId, event);
        return ResponseEntity.ok().body(result);
    }
}