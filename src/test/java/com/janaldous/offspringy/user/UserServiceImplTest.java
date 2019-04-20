package com.janaldous.offspringy.user;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.user.data.RoleRepository;
import com.janaldous.offspringy.user.data.UserRepository;
import com.janaldous.offspringy.user.data.entity.User;
import com.janaldous.offspringy.user.domain.UserModel;


@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@TestConfiguration
    static class TestContextConfiguration {
  
        @Bean
        public IUserService userService() {
            return new UserServiceImpl();
        }
        
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
        
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new PasswordEncoderMock();
        }
        
        @Bean
    	public UserEntityMapper userEntityMapper() {
        	UserEntityMapper userEntityMapper = new UserEntityMapper();
        	// TODO find a way to fix this
        	userEntityMapper.setMapper(modelMapper());
        	return userEntityMapper;
        }
    }
	
	@Autowired
	protected ModelMapper modelMapper;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	// Test data
	private String firstName = "John";
	private String lastName = "Appleseed";
	private String email = "john.appleseed@gmail.com";
	private String password = "password";
	
	@Test
	public void givenNewUser_whenRegisterUser_thenSaveUser() {
		UserModel userModel = UserModel.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.build();
		
		User user = modelMapper.map(userModel, User.class);
		user.setId(1L);
		
		given(userRepository.findByEmail(email)).willReturn(null);
		given(userRepository.save(notNull())).willReturn(user);
		
		UserModel userRegistered = userService.registerUser(userModel);
		assertEquals(userRegistered.getId(), user.getId());
		assertEquals(userRegistered.getFirstName(), user.getFirstName());
		assertEquals(userRegistered.getLastName(), user.getLastName());
		assertEquals(userRegistered.getEmail(), user.getEmail());
	}
	
	@Test(expected = UserAlreadyExistException.class)
	public void givenUserWithExistingEmail_whenRegisterUser_thenThrowError() {
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.build();
		
		UserModel userModel = modelMapper.map(user, UserModel.class);
		userModel.setId(1L);
		
		given(userRepository.findByEmail(email)).willReturn(user);
		
		userService.registerUser(userModel);
	}
}