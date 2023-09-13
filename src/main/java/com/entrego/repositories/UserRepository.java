package com.entrego.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.entity.User;

public interface UserRepository  extends JpaRepository<User, String>{
	Optional<User> findUserById(String id);
	Optional<User> findUserByDocument(String document);
	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByCell(String cell);
}
