package com.entrego.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.RegisterUserRequestDTO;
import com.entrego.domain.User;
import com.entrego.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	public User saveUser(@RequestBody RegisterUserRequestDTO user) {
		User newUser = this.userService.createUser(user);
		return newUser;
	}
	
	@GetMapping
	public List<User> allUsers() {
		return this.userService.findAllUsers();
	}
	
	@GetMapping
	@RequestMapping("/{id}")
	public User findUserById(@PathVariable String id) throws Exception {
		return this.userService.findUserById(id);
	}
	
}
