package com.entrego.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.UserDTO;
import com.entrego.entity.Address;
import com.entrego.entity.User;
import com.entrego.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private AddressService addressService;

	
	public User findUserById(String id) throws Exception {
		return this.repository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public User createUser(UserDTO data) {
		User newUser = new User(data);
		this.saveUser(newUser);
		Address newAddress = new Address(data.address().get(0));
		newAddress.setUser(newUser);
		this.addressService.saveAddress(newAddress);
		return newUser;
	}
	
	public void saveUser(User user) {
		this.repository.save(user);
	}
	
	public List<User> findAllUsers() {
		List<User> users =  this.repository.findAll();
		return users;
	}
	
}
