package com.janaldous.offspringy.activity;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.janaldous.offspringy.model.Activity;
import com.janaldous.offspringy.model.ActivityType;
import com.janaldous.offspringy.model.Event;

public interface IActivityService {

	void findByName(String name);

	List<Activity> findAll();

	Optional<Activity> findById(Long id);

	Activity save(@Valid Activity activity);

	void deleteById(Long id);

	List<Activity> search(String name, ActivityType type);

	Activity addEvent(Long id, @Valid Event event);

}