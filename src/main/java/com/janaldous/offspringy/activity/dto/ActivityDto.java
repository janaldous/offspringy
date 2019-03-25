package com.janaldous.offspringy.activity.dto;


import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.janaldous.offspringy.entity.ActivityType;

public class ActivityDto {

	@Getter
	@Setter
	@Id
    private Long id;

	@Getter
	@Setter
	@NotBlank
	@NotNull
	private String name;
	
	@Getter
	@Setter
	@NotNull
	private ActivityType type;
	
	@Getter
	@Setter
	@NotBlank
	private String summary;
}
