package com.janaldous.offspringy.business.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.business.activity.data.ActivityRepository;
import com.janaldous.offspringy.business.activity.data.entity.Activity;
import com.janaldous.offspringy.business.activity.data.entity.ActivityType;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.user.IUserService;
import com.janaldous.offspringy.user.data.entity.User;

@Service
class ActivityServiceImpl implements IActivityService {
	private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void findByName(String name) {
		log.info("Finding activity: {}", name);
		activityRepository.findByName(name);
	}

	@Override
	public List<Activity> findAll() {
		log.info("Finding all activities");
		return activityRepository.findAll();
	}

	@Override
	public Optional<Activity> getActivity(Long id) {
		log.info("Getting activity: {}", id);
		return activityRepository.findById(id);
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public Activity create(String providerEmail, Activity activity) {
		log.info("Creating activity: {} for provider {}", activity, providerEmail);
		Optional<User> provider = userService.getUserByEmail(providerEmail);
		if (provider.isPresent()) {
			activity.setProvider(provider.get());
			return activityRepository.save(activity);
		}
		throw new IllegalArgumentException("provider cannot be found");
	}
	
//	@PreAuthorize("#providerEmail == authentication.name or hasRole('ROLE_ADMIN')")
	@Override
	public void deleteById(Long id) {
		log.info("Update activity: {}", id);
		activityRepository.deleteById(id);
	}

	@Override
	public List<Activity> search(String name, ActivityType type) {
		log.info("Searching activity with query: {} {}", name, type);
		return activityRepository.findAll()
				.stream()
				.filter(activity -> name == null || activity.getName().toUpperCase().contains(name.toUpperCase()))
				.filter(activity -> type == null || activity.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@Override
	public Activity addEvent(Long id, Event event) throws EventNotFoundException {
		log.info("Adding event: {} {}", id, event);
		Activity result = getAuthorizedActivity(id);
        result.addEvent(event);
        return activityRepository.save(result);
	}
	// (returnObject.provider != null and returnObject.provider.email == authentication.name) 
	
	private Activity getAuthorizedActivity(Long id) throws EventNotFoundException {
		Optional<Activity> activityOptional = activityRepository.findById(id);
		activityOptional.orElseThrow(() -> new EventNotFoundException());
		return activityOptional.get();
	}

	@Override
	public Activity update(Activity activityUpdate) throws EventNotFoundException {
		log.info("Upating activity: {}", activityUpdate);
		Activity activity = getAuthorizedActivity(activityUpdate.getId());
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("update auth name: {}", authentication.getName());
		
		modelMapper.typeMap(Activity.class, Activity.class).setCondition(Conditions.isNotNull());
		activityUpdate.setProvider(activity.getProvider());
		modelMapper.map(activityUpdate, activity);
		
		Activity output = activityRepository.save(activity);
		return output;
	}
	
	@Override
	public Collection<Event> getEvents(Long activityId) throws ActivityNotFound {
		log.info("Getting events: {}", activityId);
		Optional<Activity> activity = activityRepository.findById(activityId);
		if (!activity.isPresent()) {
			throw new ActivityNotFound("Activity does not exist");
		}
		
		return activity.get().getEvents();
	}
}