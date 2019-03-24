package com.janaldous.offspringy.group;

import org.springframework.data.jpa.repository.JpaRepository;

import com.janaldous.offspringy.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
}