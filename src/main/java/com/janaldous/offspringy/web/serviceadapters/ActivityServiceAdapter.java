package com.janaldous.offspringy.web.serviceadapters;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.activity.ActivityNotFound;
import com.janaldous.offspringy.activity.EventNotFound;
import com.janaldous.offspringy.activity.IActivityService;
import com.janaldous.offspringy.activity.data.entity.Activity;
import com.janaldous.offspringy.activity.data.entity.ActivityType;
import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.dto.EventDto;

@Component
public class ActivityServiceAdapter {

	@Autowired
	private IActivityService activityService;
	
	public Collection<ActivityDto> search(String name, ActivityType type) {
		return activityService.search(name, type)
				.stream()
				.map(ActivityModelMapper::convertToDto)
				.collect(Collectors.toList());
	}

	public Optional<ActivityDto> findActivity(Long id) {
		Optional<Activity> activityOptional = activityService.getActivity(id);
		return Optional.of(ActivityModelMapper.convertToDto(activityOptional.get()));
	}

	public ActivityDto create(ActivityDto activity) {
		return ActivityModelMapper.convertToDto(activityService.create(ActivityModelMapper.convertToEntity(activity)));
	}

	public ActivityDto update(ActivityDto activity) throws EventNotFound {
		return ActivityModelMapper.convertToDto(activityService.update(ActivityModelMapper.convertToEntity(activity)));
	}

	public void deleteById(Long id) {
		activityService.deleteById(id);
	}

	public Collection<EventDto> getEvents(Long activityId) throws ActivityNotFound {
		return activityService.getEvents(activityId)
				.stream()
				.map(EventModelMapper::convertToEventDto)
				.collect(Collectors.toList());
	}

	public ActivityDto addEvent(Long activityId, EventDto event) {
		return ActivityModelMapper.convertToDto(
				activityService.addEvent(activityId, EventModelMapper.convertToEventEntity(event)));
	}
}
