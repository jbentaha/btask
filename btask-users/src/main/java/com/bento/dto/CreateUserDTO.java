package com.bento.dto;

public record CreateUserDTO(String name, String email, String password) {

	@Override
	public String toString() {
		return "CreateUserDTO{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
	}

}
