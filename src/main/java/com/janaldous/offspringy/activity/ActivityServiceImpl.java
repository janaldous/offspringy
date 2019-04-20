package com.janaldous.offspringy.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.activity.data.ActivityRepository;
import com.janaldous.offspringy.activity.data.entity.Activity;
import com.janaldous.offspringy.activity.data.entity.ActivityType;
import com.janaldous.offspringy.activity.data.entity.Event;

@Service
class ActivityServiceImpl implements IActivityService {
	
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
	public Optional<Activity> getActivity(Long id) {
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

//	@PreAuthorize("#activityUpdate.provider == authentication.principal.username or hasRole('ROLE_ADMIN')")
	@Override
	public Activity update(Activity activityUpdate) throws EventNotFound {
		Optional<Activity> activityOptional = activityRepository.findById(activityUpdate.getId());
		
		activityOptional.orElseThrow(() -> new EventNotFound());
		
		Activity activity = activityOptional.get();
		modelMapper.typeMap(Activity.class, Activity.class).setCondition(Conditions.isNotNull());
		modelMapper.map(activityUpdate, activity);
		
		return activityRepository.save(activity);
	}
	
	@Override
	public Collection<Event> getEvents(Long activityId) throws ActivityNotFound {
		Optional<Activity> activity = activityRepository.findById(activityId);
		if (!activity.isPresent()) {
			throw new ActivityNotFound("Activity does not exist");
		}
		
		return activity.get().getEvents();
	}
}