package com.janaldous.offspringy.web;

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

import com.janaldous.offspringy.test.config.SecurityTestConfig;
import com.janaldous.offspringy.user.UserAlreadyExistException;
import com.janaldous.offspringy.web.dto.UserDto;
import com.janaldous.offspringy.web.dto.UserRegistrationDto;
import com.janaldous.offspringy.web.serviceadapters.UserServiceAdapter;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityTestConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest extends OffspringyAbstractTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserServiceAdapter userService;
	
	@Test
	public void testGivenValidUserDto_RegisterUser() throws Exception {
		String firstName = "John";
		String lastName = "Appleseed";
		String email = "john.appleseed@gmail.com";
		String password = "apples";
		UserRegistrationDto userDto = UserRegistrationDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.password(password)
				.matchingPassword(password)
				.build();
		
		UserDto user = UserDto.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
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
		UserRegistrationDto userDto = UserRegistrationDto.builder()
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
		UserRegistrationDto userDto = UserRegistrationDto.builder()
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
