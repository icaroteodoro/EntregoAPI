package com.entrego.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.domain.User;

public interface UserRepository  extends JpaRepository<User, String>{
	Optional<User> findUserById(String id);
	Optional<User> findUserByDocument(String document);
	Optional<User> findUserByCell(String cell);
	Optional<User> findUserByAccount(com.entrego.domain.Account account);
}
