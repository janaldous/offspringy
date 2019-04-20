package com.janaldous.offspringy.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.janaldous.offspringy.activity.data.entity.ActivityType;
import com.janaldous.offspringy.test.config.SecurityTestConfig;
import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.serviceadapters.ActivityServiceAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
public class ActivityControllerTest extends OffspringyAbstractTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ActivityServiceAdapter service;
	
	@Autowired
	private ModelMapper modelMapper;

	@Test
	public void givenFindAllActivitiesQuery_whenGetActivities_thenReturn200AllActivities()
			throws Exception {
		// given
		ActivityDto activity1 = ActivityDto.builder()
				.name("activity 1")
				.summary("summary 1")
				.build();

		ActivityDto activity2 = ActivityDto.builder()
				.name("activity 2")
				.summary("summary 2")
				.build();

		List<ActivityDto> allActivities = Arrays.asList(activity1, activity2);

		given(service.search(null, null)).willReturn(allActivities);
		
		// when, then
		mvc.perform(get("/api/activity")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is(activity1.getName())))
				.andExpect(jsonPath("$[1].name", is(activity2.getName())));
	}
	
	@Test
	public void givenActivityNameQuery_whenGetActivities_thenReturn200FilteredActivities()
			throws Exception {
		// given
		ActivityDto activity1 = ActivityDto.builder()
				.name("acting class")
				.summary("acting 1")
				.build();

		Collection<ActivityDto> resetActivities = Arrays.asList(activity1);

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
	@WithMockUser(roles = "ADMIN")
	public void givenNewActivity_whenPostNewActivity_thenReturn201()
			throws Exception {
		String name = "acting class";
		String summary = "acting 1";
		String type = "BOOK_NOW";
		ActivityDto newActivity = ActivityDto.builder()
				.name(name)
				.summary(summary)
				.type(ActivityType.valueOf(type))
				.build();
		
		ActivityDto activitySaved = ActivityDto.builder()
				.id(1L)
				.name(name)
				.summary(summary)
				.type(ActivityType.valueOf(type))
				.build();
		
		given(service.create(notNull())).willReturn(activitySaved);
		
		mvc.perform(post("/api/activity")
				.content(asJsonString(newActivity))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(activitySaved.getId().intValue())));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenInvalidActivity_whenPostNewActivity_thenReturn400()
			throws Exception {
		// given
		String summary = "acting 1";
		String type = "BOOK_NOW";

		ActivityDto newActivityInvalid = ActivityDto.builder()
				.summary(summary)
				.type(ActivityType.valueOf(type))
				.build();

		mvc.perform(post("/api/activity")
				.content(asJsonString(newActivityInvalid))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenActivity_whenDeleteActivity_thenReturn200()
			throws Exception {
		
		ActivityDto activitySaved = ActivityDto.builder()
				.id(1L)
				.name("name")
				.summary("summary")
				.type(ActivityType.BOOK_NOW)
				.build();
		
		Optional<ActivityDto> activityOptional = Optional.of(activitySaved);
		
		given(service.findActivity(activitySaved.getId())).willReturn(activityOptional);
		
		mvc.perform(delete("/api/activity/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenNonExistentActivity_whenDeleteActivity_thenReturn404()
			throws Exception {
		Optional<ActivityDto> activityOptional = Optional.empty();
		
		given(service.findActivity(1L)).willReturn(activityOptional);
		
		mvc.perform(delete("/api/activity/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenUpdatedActivity_whenPutActivity_thenReturn200()
			throws Exception {
		ActivityDto activityUpdated = ActivityDto.builder()
				.id(1L)
				.name("name new")
				.summary("summary new")
				.type(ActivityType.FREE)
				.build();
		
		ActivityDto activityOrig = ActivityDto.builder()
				.id(1L)
				.name("name")
				.summary("summary")
				.type(ActivityType.BOOK_NOW)
				.build();
		
		Optional<ActivityDto> activityOptional = Optional.of(activityOrig);
		
		given(service.findActivity(1L)).willReturn(activityOptional);
		given(service.update(notNull())).willReturn(activityUpdated);
		
		mvc.perform(put("/api/activity/1")
				.content(asJsonString(activityUpdated))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void givenNewActivity_whenPutActivity_thenReturn201()
			throws Exception {
		ActivityDto newActivity = ActivityDto.builder()
				.name("acting class")
				.type(ActivityType.BOOK_NOW)
				.summary("acting 1")
				.build();
		ActivityDto activitySaved = ActivityDto.builder()
				.id(2L)
				.name("acting class")
				.type(ActivityType.BOOK_NOW)
				.summary("acting 1")
				.build();
		Optional<ActivityDto> activityEmpty = Optional.empty();
		
		given(service.findActivity(1L)).willReturn(activityEmpty);
		given(service.create(newActivity)).willReturn(activitySaved);
		
		mvc.perform(put("/api/activity/1")
				.content(asJsonString(newActivity))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
}