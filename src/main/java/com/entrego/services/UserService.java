package com.entrego.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entrego.dtos.user.request.RegisterUserRequestDTO;
import com.entrego.domain.Address;
import com.entrego.domain.User;
import com.entrego.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private com.entrego.repositories.AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private AddressService addressService;

	
	public User findUserById(String id) throws Exception {
		return this.repository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
	}

	public User findUserByEmail(String email) throws Exception {
		var account = this.accountRepository.findAccountByEmail(email).orElseThrow(() -> new Exception("User not found"));
		return this.repository.findUserByAccount(account).orElseThrow(() -> new Exception("User not found"));
	}

	public User createUser(RegisterUserRequestDTO data) {
		if(this.accountRepository.findAccountByEmail(data.email()).isPresent()) throw new RuntimeException("Email already exists");

		com.entrego.domain.Account newAccount = new com.entrego.domain.Account(data.email(), passwordEncoder.encode(data.password()), "USER");
		this.accountRepository.save(newAccount);

		User newUser = new User(data, newAccount);
		newUser = this.saveUser(newUser);

		Address newAddress = new Address(data.address().get(0));
		newAddress.setUser(newUser);
		this.addressService.saveAddress(newAddress);
		return newUser;
	}
	
	public User saveUser(User user) {
		return this.repository.save(user);
	}
	
	public List<User> findAllUsers() {
		List<User> users =  this.repository.findAll();
		return users;
	}
	
}
