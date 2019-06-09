package com.janaldous.offspringy.user;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.user.data.RoleRepository;
import com.janaldous.offspringy.user.data.UserRepository;
import com.janaldous.offspringy.user.data.entity.User;
import com.janaldous.offspringy.user.domain.UserModel;

@Service
class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserModel registerUser(UserModel userModel) {
		if (emailExists(userModel.getEmail())) {
            throw new UserAlreadyExistException("There is a user with that email adress: " + userModel.getEmail());
        }
		
		User user = User.builder()
				.firstName(userModel.getFirstName())
				.lastName(userModel.getLastName())
				.email(userModel.getLastName())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.build();
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		
		User savedUser = userRepository.save(user);
		
		UserModel savedUserModel = UserEntityMapper.convertToModel(savedUser);
		savedUserModel.setPassword(null);
		return savedUserModel;
	}
	
	private boolean emailExists(String email) {
		return userRepository.findByEmail(email) != null;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return Optional.of(userRepository.findByEmail(email));
	}
}
