package com.api.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blog.entities.User;

public interface User_Repo extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}
