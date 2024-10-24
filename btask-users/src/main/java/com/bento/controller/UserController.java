package com.bento.controller;

import com.bento.dto.CreateUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewUser(@RequestBody final CreateUserDTO newUser) {
		log.info("createNewUser {}", newUser.toString());

		//userService.createUser(userMapper.apply(newUser));
	}

	@GetMapping
	@ResponseBody
	public ResponseEntity<String> getAllUsers() {
		log.info("Get all users");

		return ResponseEntity.accepted().body("OK");
	}

}
