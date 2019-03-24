package com.janaldous.offspringy.user;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.dto.UserDto;

public interface IUserService {

	User registerUser(UserDto user);
	
}
