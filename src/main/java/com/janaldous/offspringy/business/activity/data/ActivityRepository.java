package com.janaldous.offspringy.business.activity.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.business.activity.data.entity.Activity;
import com.janaldous.offspringy.business.activity.data.entity.ActivityType;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

	List<Activity> findByName(String name);

	List<Activity> findByType(ActivityType type);
	
}
