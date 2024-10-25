package com.bento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(//
					  @NotBlank(message = "Email is mandatory")//
					  @Email(message = "Email should be valid") //
					  String email,//

					  @NotBlank(message = "Password is mandatory") //
					  String password,//

					  @NotBlank(message = "First name is mandatory") //
					  String firstName,//

					  @NotBlank(message = "Last name is mandatory") //
					  String lastName//
) {

	@Override
	public String toString() {
		return "UserDTO{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
	}

}
