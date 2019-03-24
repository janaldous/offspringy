package com.janaldous.offspringy.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
