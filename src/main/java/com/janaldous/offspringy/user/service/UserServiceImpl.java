package com.janaldous.offspringy.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.repository.UserRepository;

@Service
class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public User registerUser(User user) {
		if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is a user with that email adress: " + user.getEmail());
        }
		
		return repository.save(user);
	}
	
	@Override
	public boolean emailExists(String email) {
		return repository.findByEmail(email) != null;
	}
}
