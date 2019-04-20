package com.janaldous.offspringy.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.janaldous.offspringy.activity.data.entity.Activity;
import com.janaldous.offspringy.activity.data.entity.ActivityType;
import com.janaldous.offspringy.activity.data.entity.Event;

public interface IActivityService {

	List<Activity> findAll();

	void findByName(String name);

	List<Activity> search(String name, ActivityType type);

	Optional<Activity> getActivity(Long id);

	Collection<Event> getEvents(Long activityId) throws ActivityNotFound;

	Activity create(Activity activity);

	Activity addEvent(Long id, Event event);
	
	Activity update(Activity activityUpdated) throws EventNotFound;
	
	void deleteById(Long id);
}