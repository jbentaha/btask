package com.bento.mapper;

import com.bento.dto.CreateUserDTO;
import com.bento.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<CreateUserDTO, User> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User apply(final CreateUserDTO user) {

		return User.builder()//
				.name(user.name())//
				.email(user.email())//
				.password(passwordEncoder.encode(user.password()))//
				.build();
	}

}
