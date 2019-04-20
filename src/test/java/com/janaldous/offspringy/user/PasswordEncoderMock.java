package com.janaldous.offspringy.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderMock implements PasswordEncoder {

	private String secretCode = "[encoded]";
	
	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString() + secretCode;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String raw = rawPassword.subSequence(0, rawPassword.length()-secretCode.length()).toString();
		return raw.equals(encodedPassword);
	}
}