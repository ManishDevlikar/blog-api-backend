package com.api.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.blog.config.AppConstans;
import com.api.blog.entities.Role;
import com.api.blog.entities.User;
import com.api.blog.exceptions.ResourceNotFoundException;
import com.api.blog.paylods.UserDto;
import com.api.blog.repositories.Role_Repo;
import com.api.blog.repositories.User_Repo;
import com.api.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private User_Repo user_repo;

	@Autowired
	private Role_Repo role_Repo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.role_Repo.findById(AppConstans.NORMAL_USER).get();
		user.getRoles().add(role);

		User newUser = this.user_repo.save(user);
		return this.userToDto(newUser);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.user_repo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = this.user_repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setId(id);
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.user_repo.save(user);
		return this.userToDto(updatedUser);

	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = this.user_repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.user_repo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {

		User user = this.user_repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
		user.setRoles(null);
		this.user_repo.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
