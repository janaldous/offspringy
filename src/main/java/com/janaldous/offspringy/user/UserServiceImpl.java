package com.janaldous.offspringy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.dto.UserDto;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public User registerUser(final UserDto userDto) {
		if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is a user with that email adress: " + userDto.getEmail());
        }
		
		User user = User.builder()
				.firstName(userDto.getFirstName())
				.lastName(userDto.getLastName())
				.email(userDto.getEmail())
				.build();
		
		return repository.save(user);
	}

	private boolean emailExists(String email) {
		return repository.findByEmail(email) != null;
	}
}
