package com.janaldous.jugtours.group;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.jugtours.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
}