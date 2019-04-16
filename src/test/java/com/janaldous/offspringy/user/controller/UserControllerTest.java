package com.janaldous.offspringy.user.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.janaldous.offspringy.OffspringyTest;
import com.janaldous.offspringy.config.SecurityTestConfig;
import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.service.IUserService;
import com.janaldous.offspringy.user.service.UserAlreadyExistException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest extends OffspringyTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private IUserService userService;
	
	@Test
	public void testGivenValidUserDto_RegisterUser() throws Exception {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		String password = "apples";
		UserDto userDto = UserDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.matchingPassword(password)
				.build();
		
		User user = User.builder()
				.id(1L)
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.build();
		
		given(userService.registerUser(userDto)).willReturn(user);
		
		mvc.perform(post("/api/register")
				.content(asJsonString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void givenInvalidUserPassword_RegisterInvalidUser() throws Exception {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		String password = "apples";
		UserDto userDto = UserDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.matchingPassword("apple")
				.build();
		
		mvc.perform(post("/api/register")
				.content(asJsonString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void givenInvalidUserEmail_Return400() throws Exception {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		String password = "apples";
		UserDto userDto = UserDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.matchingPassword("apple")
				.build();
		
		given(userService.registerUser(userDto))
			.willThrow(new UserAlreadyExistException("There is a user with that email adress: " + userDto.getEmail()));
		
		mvc.perform(post("/api/register")
				.content(asJsonString(userDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
