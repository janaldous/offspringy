package com.janaldous.offspringy.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
public class Activity {
	@Id
    @GeneratedValue
    private Long id;
	@NotBlank(message = "Name is mandatory")
	@NonNull
	private String name;
	@ManyToOne
	private Provider provider;
	private ActivityType type;
	private String summary;
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<Event> events;
	
	public Activity() {
		this.events = new HashSet<>();
	}
	
	public Set<Event> getEvents() {
		return events == null ? new HashSet<>() : new HashSet<>(events);
	}
	
	public void setEvents(Set<Event> events) {
		this.events = new HashSet<>(events);
	}

	public void addEvent(Event event) {
		if (events == null) {
			events = new HashSet<>();
		}
		events.add(event);
	}
}
