package com.atdaiwa.crowd.mvc.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.atdaiwa.crowd.util.CrowdUtil;

public class MyPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return CrowdUtil.toMD5(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(CrowdUtil.toMD5(rawPassword.toString()));
	}
}
