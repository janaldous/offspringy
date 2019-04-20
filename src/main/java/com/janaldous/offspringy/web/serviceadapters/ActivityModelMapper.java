package com.janaldous.offspringy.web.serviceadapters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.activity.data.entity.Activity;
import com.janaldous.offspringy.web.dto.ActivityDto;

@Component
class ActivityModelMapper {
	
	@Autowired
	private static ModelMapper modelMapper;
	
	static ActivityDto convertToDto(Activity activity) {
		ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
		activityDto.setProvider(activity.getProvider() == null ? null : activity.getProvider().getEmail());
		return activityDto;
	}

	static Activity convertToEntity(ActivityDto activityDto) {
		Activity activity = modelMapper.map(activityDto, Activity.class);
		return activity;
	}
}
