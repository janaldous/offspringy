package com.janaldous.offspringy.activity;

import io.swagger.annotations.Api;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.janaldous.offspringy.activity.dto.ActivityDto;
import com.janaldous.offspringy.activity.dto.EventDto;
import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.Event;

@RestController
@RequestMapping("/api")
@Api(value="event")
public class EventController {
	
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
    private IActivityService activityService;
	
	@Autowired
	private IEventService eventService;
	
	@Autowired
	private ModelMapper modelMapper;
    
	@GetMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) {
        Optional<Event> event = eventService.getEvent(eventId);
        if (event.isPresent()) {
        	return ResponseEntity.ok().body(convertToEventDto(event.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@PutMapping("/activity/{activityId}/event/{eventId}")
    ResponseEntity<EventDto> updateEvent(@Valid @RequestBody EventDto event, @PathVariable Long activityId) {
		log.info("Request to update event: {}", event);
		
		if (!activityService.findActivity(activityId).isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		EventDto result = convertToEventDto(eventService.add(convertToEventEntity(event)));
        return ResponseEntity.ok().body(result);
    }
	
	@PutMapping("/activity/{id}/event")
    ResponseEntity<ActivityDto> addEventToActivity(@Valid @RequestBody EventDto event, @PathVariable Long id) {
        log.info("Request to add event: {}", event);
        ActivityDto result = convertToActivityDto(activityService.addEvent(id, convertToEventEntity(event)));
        return ResponseEntity.ok().body(result);
    }
	
	private EventDto convertToEventDto(Event event) {
		return modelMapper.map(event, EventDto.class);
	}
	
	private Event convertToEventEntity(EventDto eventDto) {
		return modelMapper.map(eventDto, Event.class);
	}
	
	private ActivityDto convertToActivityDto(Activity activity) {
		ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
	    return activityDto;
	}
	
}
