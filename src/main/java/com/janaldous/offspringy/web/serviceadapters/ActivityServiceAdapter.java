package com.janaldous.offspringy.web.serviceadapters;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.business.activity.EventNotFoundException;
import com.janaldous.offspringy.business.activity.IActivityService;
import com.janaldous.offspringy.business.activity.data.entity.Activity;
import com.janaldous.offspringy.business.activity.data.entity.ActivityType;
import com.janaldous.offspringy.web.dto.ActivityDto;

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

	public ActivityDto create(String currentUserEmail, ActivityDto activity, Long id) {
		Activity activity2 = ActivityModelMapper.convertToEntity(activity);
		activity2.setId(id);
		return ActivityModelMapper.convertToDto(activityService.create(currentUserEmail, activity2));
	}

	public ActivityDto update(ActivityDto activity) throws EventNotFoundException {
		return ActivityModelMapper.convertToDto(activityService.update(ActivityModelMapper.convertToEntity(activity)));
	}

	public void deleteById(Long id) {
		activityService.deleteById(id);
	}
}
