package com.janaldous.offspringy.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.activity.dto.ActivityDto;
import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;
import com.janaldous.offspringy.entity.Event;

@Service
public class ActivityServiceImpl implements IActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void findByName(String name) {
		activityRepository.findByName(name);
	}

	@Override
	public List<Activity> findAll() {
		return activityRepository.findAll();
	}

	@Override
	public Optional<Activity> findActivity(Long id) {
		return activityRepository.findById(id);
	}

	@Override
	public Activity create(Activity activity) {
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

	@Override
	public boolean hasActivity(Long id) {
		return activityRepository.findById(id).isPresent();
	}

//	@PreAuthorize("#activityUpdate.provider == authentication.principal.username or hasRole('ROLE_ADMIN')")
	@Override
	public Activity update(ActivityDto activityUpdate) throws EventDoesNotExistException {
		Optional<Activity> activityOptional = activityRepository.findById(activityUpdate.getId());
		
		activityOptional.orElseThrow(() -> new EventDoesNotExistException());
		
		Activity activity = activityOptional.get();
		modelMapper.typeMap(Activity.class, Activity.class).setCondition(Conditions.isNotNull());
		modelMapper.map(activityUpdate, activity);
		
		return activityRepository.save(activity);
	}
	
	@Override
	public Collection<Event> getEvents(Long activityId) throws ActivityDoesNotExistException {
		Optional<Activity> activity = activityRepository.findById(activityId);
		if (!activity.isPresent()) {
			throw new ActivityDoesNotExistException("Activity does not exist");
		}
		
		return activity.get().getEvents();
	}
}