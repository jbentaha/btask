package com.bento.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public/users")
public class PublicUserController {

	@GetMapping
	@ResponseBody
	public ResponseEntity<String> getAllUsers() {
		log.info("Get all users");

		return ResponseEntity.accepted().body("OK");
	}

}
