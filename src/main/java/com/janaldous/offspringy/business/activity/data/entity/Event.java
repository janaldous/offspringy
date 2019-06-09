package com.janaldous.offspringy.business.activity.data.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.janaldous.offspringy.user.data.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private String title;
    private String description;
    @JsonIgnore
    @ManyToMany
    private Set<User> attendees;
	private boolean isCancellable;
	private int capacity;
}