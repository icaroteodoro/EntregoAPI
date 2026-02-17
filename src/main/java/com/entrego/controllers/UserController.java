package com.entrego.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.user.request.RegisterUserRequestDTO;
import com.entrego.dtos.user.response.UserResponseDTO;
import com.entrego.domain.User;
import com.entrego.services.UserService;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	public UserResponseDTO saveUser(@RequestBody RegisterUserRequestDTO user) {
		User newUser = this.userService.createUser(user);
		return new UserResponseDTO(newUser);
	}
	
	@GetMapping
	public List<UserResponseDTO> allUsers() {
		return this.userService.findAllUsers().stream().map(UserResponseDTO::new).collect(Collectors.toList());
	}
	
	@GetMapping
	@RequestMapping("/{id}")
	public UserResponseDTO findUserById(@PathVariable String id) throws Exception {
		return new UserResponseDTO(this.userService.findUserById(id));
	}
	
}
