package com.janaldous.offspringy.user.service;

import com.janaldous.offspringy.entity.User;

public interface IUserService {

	User registerUser(User user);

	boolean emailExists(String email);
}
