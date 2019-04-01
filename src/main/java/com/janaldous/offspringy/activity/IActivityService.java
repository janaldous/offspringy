package com.janaldous.offspringy.activity;

import java.util.List;
import java.util.Optional;

import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;
import com.janaldous.offspringy.entity.Event;

public interface IActivityService {

	void findByName(String name);

	List<Activity> findAll();

	Optional<Activity> findActivity(Long id);

	Activity save(Activity activity);

	void deleteById(Long id);

	List<Activity> search(String name, ActivityType type);

	Activity addEvent(Long id, Event event);

	boolean hasActivity(Long id);

	Activity update(Long id, Activity convertToEntity);

}