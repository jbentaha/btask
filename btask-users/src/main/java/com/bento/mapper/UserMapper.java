package com.bento.mapper;

import com.bento.dto.UserDTO;
import com.btask.user.BUser;
import com.btask.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserMapper implements Function<UserDTO, BUser> {

	private final PasswordEncoder passwordEncoder;

	@Override
	public BUser apply(final UserDTO userDTO) {

		return BUser.builder()//
				.firstName(userDTO.firstName())//
				.lastName(userDTO.lastName())//
				.email(userDTO.email())//
				.password(passwordEncoder.encode(userDTO.password()))//
				.roles(List.of(UserRole.USER))//
				.build();
	}

}
