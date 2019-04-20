package com.janaldous.offspringy.web.serviceadapters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.janaldous.offspringy.user.domain.UserModel;
import com.janaldous.offspringy.web.dto.UserDto;
import com.janaldous.offspringy.web.dto.UserRegistrationDto;

class UserModelMapper {
	
	@Autowired
	private static ModelMapper modelMapper;
	
	static UserModel convertToModel(UserDto user) {
		return modelMapper.map(user, UserModel.class);
	}
	
	static UserDto convertToDto(UserModel userModel) {
		return modelMapper.map(userModel, UserDto.class);
	}

	static UserModel convertToModel(UserRegistrationDto userDto) {
		return modelMapper.map(userDto, UserModel.class);
	}
}
