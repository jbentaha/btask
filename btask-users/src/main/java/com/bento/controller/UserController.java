package com.bento.controller;

import com.bento.dto.CreateUserDTO;
import com.bento.mapper.UserMapper;
import com.bento.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewUser(@RequestBody final CreateUserDTO newUser) {
		log.info("createNewUser {}", newUser.toString());

		userService.createUser(userMapper.apply(newUser));
	}

}