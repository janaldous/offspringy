package com.janaldous.offspringy.test.config;

import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.userdetails.User;

@TestConfiguration
public class SecurityTestConfig {

	@Bean
	@Primary
	public UserDetailsService myUserDetailsService() {
		User basicUser = new User("user@company.com", "password",
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

		User managerUser = new User("admin@company.com", "admin",
				Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		return new InMemoryUserDetailsManager(Arrays.asList(basicUser,
				managerUser));
	}
}
