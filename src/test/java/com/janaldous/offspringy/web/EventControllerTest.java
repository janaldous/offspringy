package com.janaldous.offspringy.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.dto.EventDto;
import com.janaldous.offspringy.web.serviceadapters.ActivityServiceAdapter;
import com.janaldous.offspringy.web.serviceadapters.EventServiceAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ActivityServiceAdapter service;
	
	@MockBean
	private EventServiceAdapter eventService;
	
	@Test
	public void givenNonExistentActivity_whenGetEvents_thenReturn404()
			throws Exception {
		Optional<ActivityDto> activityOptional = Optional.empty();

		given(service.findActivity(1L)).willReturn(activityOptional);
		
		mvc.perform(get("/api/activity/1/event")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenActivityQuery_whenGetEvents_thenReturnEvents()
			throws Exception {
		ActivityDto activity1 = ActivityDto.builder()
				.name("acting class")
				.summary("acting 1")
				.build();
		
		EventDto event1 = EventDto.builder()
				.date(LocalDate.of(2015, 02, 20))
				.title("summer class")
				.description("summer classes desc")
				.build();
		
		Long activityId = 1L;

		Optional<ActivityDto> activityOptional = Optional.of(activity1);

		Collection<EventDto> events = Arrays.asList(event1);
		
		given(service.findActivity(activityId)).willReturn(activityOptional);
		given(eventService.getAll(activityId)).willReturn(events);
		
		mvc.perform(get("/api/activity/1/event")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}
}