package com.api.blog.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.blog.entities.User;
import com.api.blog.exceptions.ApiException;
import com.api.blog.paylods.JwtAuthRequest;
import com.api.blog.paylods.JwtAuthResponse;
import com.api.blog.paylods.UserDto;
import com.api.blog.security.JwtTokenHelper;
import com.api.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtTokenHelper helper;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper mapper;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@Validated @RequestBody JwtAuthRequest request) {

		this.doAuthenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.helper.generateToken(userDetails);

		JwtAuthResponse response = JwtAuthResponse.builder().token(token).build();
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			throw new ApiException(" Invalid Username or Password  !!");
		}

	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@Validated @RequestBody UserDto userDto) {
		UserDto newUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}

}
