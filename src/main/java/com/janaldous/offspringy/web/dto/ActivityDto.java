package com.janaldous.offspringy.web.dto;


import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.janaldous.offspringy.activity.data.entity.ActivityType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	
	@Getter
	@Setter
	private String provider;
}
