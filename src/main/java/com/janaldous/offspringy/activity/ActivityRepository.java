package com.janaldous.offspringy.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.model.Activity;
import com.janaldous.offspringy.model.ActivityType;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	List<Activity> findByName(String name);

	List<Activity> findByType(ActivityType type);
	
}
