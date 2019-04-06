package com.janaldous.offspringy.user.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.janaldous.offspringy.validation.PasswordMatches;
import com.janaldous.offspringy.validation.ValidEmail;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
