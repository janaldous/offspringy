package com.janaldous.offspringy.web.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.janaldous.offspringy.business.activity.data.entity.ActivityType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ApiModel(description = "All details about the Activity. ")
public class ActivityDto {

	@Getter
	@Setter
	@NotBlank
	@NotNull
	@ApiModelProperty(notes = "Name of the activity")
	private String name;
	
	@Getter
	@Setter
	@NotNull
	@ApiModelProperty(notes = "The type of the activity")
	private ActivityType type;
	
	@Getter
	@Setter
	@NotBlank
	@ApiModelProperty(notes = "A short summay of the activity")
	private String summary;
	
	@Getter
	@Setter
	@ApiModelProperty(notes = "The email of the provider")
	private String provider;
}
