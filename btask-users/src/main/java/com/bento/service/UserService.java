package com.bento.service;

import com.bento.dto.UserDTO;
import com.bento.exception.EmailAlreadyExistsException;
import com.bento.mapper.UserMapper;
import com.btask.user.BUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final BUserRepository userRepository;

	private final UserMapper userMapper;

	public void addUser(final UserDTO userDTO) {
		if (userRepository.existsByEmail(userDTO.email())) {
			throw new EmailAlreadyExistsException("Email is already in use : " + userDTO.email());
		}

		userRepository.save(userMapper.apply(userDTO));
	}

}
