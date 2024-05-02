package com.api.blog.paylods;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 3, message = "username must be more than 3 character")
	private String name;
	@Email(message = "enter valid email id")
	@NotEmpty(message = "email is require")
	private String email;
	@NotEmpty
	@Size(min = 4, max = 8, message = "password should have 4 to 8 charater")
	private String password;
	@NotEmpty
	private String about;

	Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

}
