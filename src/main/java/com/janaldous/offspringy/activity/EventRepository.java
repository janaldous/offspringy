package com.janaldous.jugtours.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.jugtours.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
