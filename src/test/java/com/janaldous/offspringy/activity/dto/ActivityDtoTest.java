package com.janaldous.offspringy.activity.dto;

import static org.junit.Assert.*;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;
import com.janaldous.offspringy.entity.Provider;


public class ActivityDtoTest {

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	public void whenConvertActivityEntityToActivityDto_thenCorrect() {
		Provider provider = Provider.builder()
				.name("provider")
				.build();

		Activity activity = new Activity();
		activity.setId(1L);
		activity.setName("activity name");
		activity.setType(ActivityType.BOOK_NOW);
		activity.setProvider(provider);
		activity.setSummary("Summary");

		ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
		assertEquals(activity.getId(), activityDto.getId());
		assertEquals(activity.getName(), activityDto.getName());
		assertEquals(activity.getType(), activityDto.getType());
		assertEquals(activity.getProvider(), activityDto.getProvider());
		assertEquals(activity.getSummary(), activityDto.getSummary());
	}

	@Test
	public void whenConvertActivityDtoToActivityEntity_thenCorrect() {
		Provider provider = Provider.builder()
				.name("provider")
				.build();

		ActivityDto activityDto = new ActivityDto();
		activityDto.setId(1L);
		activityDto.setName("activityDto name");
		activityDto.setType(ActivityType.BOOK_NOW);
		activityDto.setProvider(provider);
		activityDto.setSummary("Summary");

		Activity activity = modelMapper.map(activityDto, Activity.class);
		assertEquals(activityDto.getId(), activity.getId());
		assertEquals(activityDto.getName(), activity.getName());
		assertEquals(activityDto.getType(), activity.getType());
		assertEquals(activityDto.getProvider(), activity.getProvider());
		assertEquals(activityDto.getSummary(), activity.getSummary());
	}

}
