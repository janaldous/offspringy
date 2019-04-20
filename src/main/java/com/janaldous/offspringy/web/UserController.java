package com.janaldous.offspringy.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.web.dto.UserDto;
import com.janaldous.offspringy.web.dto.UserRegistrationDto;
import com.janaldous.offspringy.web.serviceadapters.UserServiceAdapter;

@RestController
@RequestMapping("/api")
@Api(value="user")
public class UserController {
	
	@Autowired
	private UserServiceAdapter userService;
	
	@ApiOperation(value = "Register a user", response = ResponseEntity.class)
	@PostMapping("/register")
    ResponseEntity<UserDto> register(@Valid @RequestBody UserRegistrationDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}
