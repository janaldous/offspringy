package com.janaldous.offspringy;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.business.activity.data.ActivityRepository;
import com.janaldous.offspringy.business.activity.data.EventRepository;
import com.janaldous.offspringy.business.activity.data.entity.Activity;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.business.group.Group;
import com.janaldous.offspringy.business.group.GroupRepository;
import com.janaldous.offspringy.user.data.UserRepository;
import com.janaldous.offspringy.user.data.entity.User;

@Component
class Initializer implements CommandLineRunner {
	@Autowired
    private final GroupRepository repository;
    
	@Autowired
    private ActivityRepository activityRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
    private UserRepository userRepository;
	
    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
    	User provider = userRepository.findByEmail("john@provider.com");

    	Stream.of("Activity 1", "Activity 12", "Activity 13",
                "Activity 14").forEach(name ->
                activityRepository.save(Activity.builder().name(name).provider(provider).build())
        );
    	
    	Event e1 = Event.builder().title("Event 1").description("Description").capacity(2).build();
    	Event e2 = Event.builder().title("Event 2").description("Description").capacity(2).build();
    	
    	eventRepository.save(e1);
    	eventRepository.save(e2);
    	
    	Activity activity = activityRepository.findAll().get(0);
    	activity.setProvider(provider);
    	activity.addEvent(e1);
    	activity.addEvent(e2);
    	
    	Activity afterSave = activityRepository.save(activity);
    	assert 2 == afterSave.getEvents().size() : "events not saved correctly";
    	
    	activityRepository.findAll().forEach(System.out::println);
    	
    	Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name ->
                repository.save(new Group(name))
        );

        Group djug = repository.findByName("Denver JUG");
        Event e = Event.builder().title("Full Stack Reactive")
                .description("Reactive with Spring Boot + React")
                .date(new Date())
                .build();
        djug.setEvents(Collections.singleton(e));
        repository.save(djug);

        repository.findAll().forEach(System.out::println);
    }
}