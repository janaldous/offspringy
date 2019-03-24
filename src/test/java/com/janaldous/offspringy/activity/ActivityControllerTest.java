package com.janaldous.offspringy.activity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;

@RunWith(SpringRunner.class)
@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IActivityService service;

	@Test
	public void givenActivities_whenGetActivities_thenReturnJsonArray()
			throws Exception {
		// given
		Activity activity1 = Activity.builder()
				.name("activity 1")
				.summary("summary 1")
				.build();

		Activity activity2 = Activity.builder()
				.name("activity 2")
				.summary("summary 2")
				.build();

		List<Activity> allActivities = Arrays.asList(activity1, activity2);

		given(service.findAll()).willReturn(allActivities);
		
		// when, then
		mvc.perform(get("/api/activity")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is(activity1.getName())))
				.andExpect(jsonPath("$[1].name", is(activity2.getName())));
	}
	
	@Test
	public void givenActivityQuery_whenGetActivities_thenReturnJsonArray()
			throws Exception {
		// given
		Activity activity1 = Activity.builder()
				.name("acting class")
				.summary("acting 1")
				.build();

		List<Activity> resetActivities = Arrays.asList(activity1);

		given(service.search("act", null)).willReturn(resetActivities);
		
		// when, then
		mvc.perform(get("/api/activity")
				.param("name", "act")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(activity1.getName())));
	}
	
	@Test
	public void givenNewActivity_whenPostNewActivity_thenReturnJson()
			throws Exception {
		// given
		String name = "acting class";
		String summary = "acting 1";
		String type = "BOOK_NOW";
		String json = "{"
				+ "\"name\": \"" + name + "\","
				+ "\"summary\": \"" + summary + "\","
				+ "\"type\": \"" + type + "\""
				+ "}";
		Activity activity1 = Activity.builder()
				.name(name)
				.summary(summary)
				.type(ActivityType.valueOf(type))
				.build();
		
		Activity activitySaved = Activity.builder()
				.id(1L)
				.name(name)
				.summary(summary)
				.type(ActivityType.valueOf(type))
				.build();
		
		given(service.save(activity1)).willReturn(activitySaved);
		
		// when, then
		mvc.perform(post("/api/activity")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(activitySaved.getId().intValue())));
	}
	
	@Test
	public void givenInvalidActivity_whenPostNewActivity_thenReturnValidationError()
			throws Exception {
		// given
		String summary = "acting 1";
		String type = "BOOK_NOW";
		String json = "{"
				+ "\"summary\": \"" + summary + "\","
				+ "\"type\": \"" + type + "\""
				+ "}";
		
		// when, then
		mvc.perform(post("/api/activity")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenActivity_whenDeleteActivity_thenReturn200()
			throws Exception {
		// given
		Activity activity = Activity.builder()
				.id(1L)
				.name("name")
				.summary("summary")
				.type(ActivityType.BOOK_NOW)
				.build();
		Optional<Activity> activityResult = Optional.of(activity);
		
		given(service.findById(1L)).willReturn(activityResult);
		
		// when, then
		mvc.perform(delete("/api/activity/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenNonExistentActivity_whenDeleteActivity_thenReturn404()
			throws Exception {
		// given
		Optional<Activity> activityResult = Optional.empty();
		
		given(service.findById(1L)).willReturn(activityResult);
		
		// when, then
		mvc.perform(delete("/api/activity/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void givenActivity_whenUpdateActivity_thenReturn200()
			throws Exception {
		// given
		Activity activity = Activity.builder()
				.id(1L)
				.name("name")
				.summary("summary")
				.type(ActivityType.BOOK_NOW)
				.build();
		Optional<Activity> activityResult = Optional.of(activity);
		
		given(service.findById(1L)).willReturn(activityResult);
		
		// when, then
		mvc.perform(put("/api/activity/1")
				.content(asJsonString(activity))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenNonExistentActivity_whenUpdateActivity_thenReturn404()
			throws Exception {
		// given
		Activity activity = Activity.builder()
				.id(101L)
				.name("acting class")
				.summary("acting 1")
				.build();
		Optional<Activity> activityResult = Optional.empty();
		
		given(service.findById(1L)).willReturn(activityResult);
		
		// when, then
		mvc.perform(put("/api/activity/1")
				.content(asJsonString(activity))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}