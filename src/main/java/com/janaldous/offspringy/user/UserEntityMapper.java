package com.janaldous.offspringy.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.user.data.entity.User;
import com.janaldous.offspringy.user.domain.UserModel;

@Component
public class UserEntityMapper {
	
	@Autowired
	private static ModelMapper modelMapper;
	
	public static UserModel convertToModel(User user) {
		return modelMapper.map(user, UserModel.class);
	}

	public void setMapper(ModelMapper modelMapper) {
		UserEntityMapper.modelMapper = modelMapper;
	}
}
