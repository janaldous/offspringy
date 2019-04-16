package com.janaldous.offspringy.user.service;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.controller.UserDto;

public interface IUserService {

	User registerUser(UserDto user);

	boolean emailExists(String email);
}
