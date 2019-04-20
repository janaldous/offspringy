package com.janaldous.offspringy.web.serviceadapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.user.IUserService;
import com.janaldous.offspringy.web.dto.UserDto;
import com.janaldous.offspringy.web.dto.UserRegistrationDto;

@Component
public class UserServiceAdapter {

	@Autowired
	private IUserService userService;
	
	public UserDto registerUser(UserRegistrationDto userDto) {
		return UserModelMapper.convertToDto(userService.registerUser(UserModelMapper.convertToModel(userDto)));
	}
}
