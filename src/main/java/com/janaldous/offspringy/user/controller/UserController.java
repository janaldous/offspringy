package com.janaldous.offspringy.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.service.IUserService;

@RestController
@RequestMapping("/api")
@Api(value="user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Register a user", response = ResponseEntity.class)
	@PostMapping("/register")
    ResponseEntity<User> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
}
