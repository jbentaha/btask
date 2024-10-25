package com.bento.controller;

import com.bento.dto.LoginRequest;
import com.bento.dto.UserDTO;
import com.bento.security.token.JwtTokenUtil;
import com.bento.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/public")
public class PublicUserController {

	private final UserService userService;

	private final JwtTokenUtil tokenUtil;

	private final AuthenticationManager authenticationManager;

	@GetMapping
	@ResponseBody
	public ResponseEntity<String> getAllUsers() {
		log.info("Get all users");

		return ResponseEntity.accepted().body("OK yeeey");
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody @Valid final UserDTO userDTO) {
		log.info("Creating new user: {}", userDTO);

		userService.addUser(userDTO);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> login(@RequestBody @Valid final LoginRequest loginRequestDTO) {
		log.info("Connecting user: {}", loginRequestDTO.email());

		final Authentication auth = authenticationManager.authenticate(//
				new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));

		if (auth.isAuthenticated()) {
			final String token = tokenUtil.generateToken((UserDetails) auth.getPrincipal());
			return ResponseEntity.accepted().body(token);
		}

		return ResponseEntity.notFound().build();
	}

}
