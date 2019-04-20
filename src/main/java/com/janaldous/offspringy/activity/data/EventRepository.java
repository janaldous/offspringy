package com.janaldous.offspringy.activity.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.activity.data.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
