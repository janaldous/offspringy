package com.janaldous.offspringy.user.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.controller.UserDto;
import com.janaldous.offspringy.user.repository.UserRepository;


@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@Autowired
	protected ModelMapper modelMapper;
	
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
    }
	
	@Autowired
	private IUserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void testRegisterUser() {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		UserDto user = UserDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.build();
		
		User user1 = modelMapper.map(user, User.class);
		
		User userSaved = User.builder()
				.id(1L)
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.build();
		
		given(userRepository.findByEmail(email)).willReturn(null);
		given(userRepository.save(user1)).willReturn(userSaved);
		
		User userRegistered = userService.registerUser(user);
		assertEquals(userRegistered.getId(), userSaved.getId());
		assertEquals(userRegistered.getFirstName(), userSaved.getFirstName());
		assertEquals(userRegistered.getLastName(), userSaved.getLastName());
		assertEquals(userRegistered.getEmail(), userSaved.getEmail());
	}
	
	@Test(expected = UserAlreadyExistException.class)
	public void testGivenUserWithExistingEmail_RegisterUser_throwError() {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.build();
		
		UserDto userDto = modelMapper.map(user, UserDto.class);
		
		given(userRepository.findByEmail(email)).willReturn(user);
		
		userService.registerUser(userDto);
	}
}
