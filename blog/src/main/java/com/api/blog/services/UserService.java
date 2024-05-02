package com.api.blog.services;

import java.util.List;

import com.api.blog.paylods.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer id);

	UserDto getUserById(Integer id);

	List<UserDto> getAllUser();

	void deleteUser(Integer id);
}
