package com.janaldous.offspringy.business.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import com.janaldous.offspringy.business.activity.data.entity.Activity;
import com.janaldous.offspringy.business.activity.data.entity.ActivityType;
import com.janaldous.offspringy.business.activity.data.entity.Event;

public interface IActivityService {

	List<Activity> findAll();

	void findByName(String name);

	List<Activity> search(String name, ActivityType type);

	Optional<Activity> getActivity(Long id);

	Collection<Event> getEvents(Long activityId) throws ActivityNotFound;

	Activity create(String providerEmail, Activity activity);
	
	Activity addEvent(Long id, Event event) throws EventNotFoundException;
	
	@PreAuthorize("hasRole('ADMIN')")
	Activity update(Activity activityUpdated) throws EventNotFoundException;
	
	void deleteById(Long id);
}