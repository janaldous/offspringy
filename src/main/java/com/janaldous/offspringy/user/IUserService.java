package com.janaldous.offspringy.user;

import java.util.Optional;

import com.janaldous.offspringy.user.data.entity.User;
import com.janaldous.offspringy.user.domain.UserModel;


public interface IUserService {

	UserModel registerUser(UserModel user);
	
	Optional<User> getUserByEmail(String email);
}
