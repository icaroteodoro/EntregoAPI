package com.entrego.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.UserDTO;
import com.entrego.entity.User;
import com.entrego.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	public User saveUser(@RequestBody UserDTO user) {
		User newUser = this.userService.createUser(user);
		return newUser;
	}
	
	@GetMapping
	public List<User> allUsers() {
		return userService.findAllUsers();
	}
	
}
