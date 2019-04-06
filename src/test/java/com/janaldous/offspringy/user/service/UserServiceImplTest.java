package com.janaldous.offspringy.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.repository.UserRepository;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@TestConfiguration
    static class TestContextConfiguration {
  
        @Bean
        public IUserService userService() {
            return new UserServiceImpl();
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
		User user = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.build();
		
		User userSaved = User.builder()
				.id(1L)
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.build();
		
		given(userRepository.findByEmail(email)).willReturn(null);
		given(userRepository.save(user)).willReturn(userSaved);
		
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
		
		given(userRepository.findByEmail(email)).willReturn(user);
		
		userService.registerUser(user);
	}
}
