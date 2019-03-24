package com.janaldous.offspringy.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import com.janaldous.offspringy.validation.PasswordMatches;
import com.janaldous.offspringy.validation.ValidEmail;

@PasswordMatches
public class UserDto {
    
	@Getter
	@Setter
	@NotNull
    @NotEmpty
    private String firstName;
    
	@Getter
	@Setter
    @NotNull
    @NotEmpty
    private String lastName;
     
	@Getter
	@Setter
    @NotNull
    @NotEmpty
    private String password;
	
	@Getter
	@Setter
    private String matchingPassword;
     
	@Getter
	@Setter
	@ValidEmail
    @NotNull
    @NotEmpty
    private String email;
     
}
