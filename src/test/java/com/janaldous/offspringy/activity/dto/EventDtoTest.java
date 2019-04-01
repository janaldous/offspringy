package com.janaldous.offspringy.activity.dto;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.janaldous.offspringy.entity.Event;

public class EventDtoTest {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Test
	public void whenConvertEventEntityToEventDto_thenCorrect() {
		Event event = new Event();
		event.setId(1L);
		event.setDate(new Date());
		event.setTitle("title");
		event.setDescription("Description");
		
		EventDto eventDto = modelMapper.map(event, EventDto.class);
		assertEquals(event.getId(), eventDto.getId());
		assertEquals(event.getDate(), eventDto.getDate());
		assertEquals(event.getTitle(), eventDto.getTitle());
		assertEquals(event.getDescription(), eventDto.getDescription());
	}

	@Test
	public void whenConvertActivityDtoToActivityEntity_thenCorrect() {
		EventDto eventDto = new EventDto();
		eventDto.setId(1L);
		eventDto.setDate(new Date());
		eventDto.setTitle("title");
		eventDto.setDescription("Description");
		
		Event event = modelMapper.map(eventDto, Event.class);
		assertEquals(event.getId(), eventDto.getId());
		assertEquals(event.getDate(), eventDto.getDate());
		assertEquals(event.getTitle(), eventDto.getTitle());
		assertEquals(event.getDescription(), eventDto.getDescription());
	}

}
