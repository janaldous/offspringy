package com.janaldous.offspringy.user.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.controller.UserDto;
import com.janaldous.offspringy.user.repository.RoleRepository;
import com.janaldous.offspringy.user.repository.UserRepository;

@Service
class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User registerUser(UserDto userDto) {
		if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is a user with that email adress: " + userDto.getEmail());
        }
		
		User user = User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getLastName())
				.password(passwordEncoder.encode(userDto.getPassword()))
				.build();
		
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		
		return userRepository.save(user);
	}
	
	@Override
	public boolean emailExists(String email) {
		return userRepository.findByEmail(email) != null;
	}
}
