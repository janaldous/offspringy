package com.janaldous.offspringy;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.activity.ActivityRepository;
import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.Event;
import com.janaldous.offspringy.entity.Group;
import com.janaldous.offspringy.group.GroupRepository;

@Component
class Initializer implements CommandLineRunner {

    private final GroupRepository repository;
    @Autowired
    private ActivityRepository activityRepository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
    	Stream.of("Activity 1", "Activity 12", "Activity 13",
                "Activity 14").forEach(name ->
                activityRepository.save(new Activity(name))
        );
    	Activity activity = activityRepository.findById((long) 1).get();
    	activity.getEvents().add(Event.builder().title("Event 1").description("Description").build());
    	activity.getEvents().add(Event.builder().title("Event 2").description("Description").build());
    	
    	activityRepository.save(activity);
    	
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
