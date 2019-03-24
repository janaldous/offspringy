package com.janaldous.offspringy.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.entity.User;
import com.janaldous.offspringy.user.dto.UserDto;

@RestController
@RequestMapping("/api")
@Api(value="user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@ApiOperation(value = "Register a user", response = ResponseEntity.class)
	@GetMapping("/register")
    ResponseEntity<User> register(@Valid UserDto user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }
}
