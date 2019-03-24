package com.janaldous.offspringy.activity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;
import com.janaldous.offspringy.entity.Event;

@Service
public class ActivityServiceImpl implements IActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public void findByName(String name) {
		activityRepository.findByName(name);
	}

	@Override
	public List<Activity> findAll() {
		return activityRepository.findAll();
	}

	@Override
	public Optional<Activity> findById(Long id) {
		return activityRepository.findById(id);
	}

	@Override
	public Activity save(@Valid Activity activity) {
		return activityRepository.save(activity);
	}

	@Override
	public void deleteById(Long id) {
		activityRepository.deleteById(id);
	}

	@Override
	public List<Activity> search(String name, ActivityType type) {
		return activityRepository.findAll()
				.stream()
				.filter(activity -> name == null || activity.getName().toUpperCase().contains(name.toUpperCase()))
				.filter(activity -> type == null || activity.getType().equals(type))
				.collect(Collectors.toList());
	}

	@Override
	public Activity addEvent(Long id, @Valid Event event) {
		Activity result = activityRepository.findById(id).get();
        result.addEvent(event);
        return activityRepository.save(result);
	}
	
}
