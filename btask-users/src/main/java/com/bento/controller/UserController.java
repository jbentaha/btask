package com.bento.controller;

import com.bento.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users/private")
public class UserController {

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewUser(@RequestBody final UserDTO newUser) {
		log.info("createNewUser {}", newUser.toString());

		//userService.createUser(userMapper.apply(newUser));
	}

	@GetMapping("/all")
	@ResponseBody
	public ResponseEntity<String> getAllUsers() {
		log.info("Get all users");

		return ResponseEntity.accepted().body("OK private");
	}

}
