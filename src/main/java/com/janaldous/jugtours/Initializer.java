package com.janaldous.jugtours;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.janaldous.jugtours.activity.ActivityRepository;
import com.janaldous.jugtours.group.GroupRepository;
import com.janaldous.jugtours.model.Activity;
import com.janaldous.jugtours.model.Event;
import com.janaldous.jugtours.model.Group;

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
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();
        djug.setEvents(Collections.singleton(e));
        repository.save(djug);

        repository.findAll().forEach(System.out::println);
    }
}
