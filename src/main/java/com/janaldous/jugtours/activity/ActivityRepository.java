package com.janaldous.jugtours.activity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.jugtours.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
