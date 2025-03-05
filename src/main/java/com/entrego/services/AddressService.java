package com.entrego.services;

import java.util.List;

import com.entrego.domain.User;
import com.entrego.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.AddressDTO;
import com.entrego.domain.Address;
import com.entrego.repositories.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository repository;

	@Autowired
	private UserRepository userRepository;

	public List<Address> findAllAddress(){
		return this.repository.findAll();
	}

	public Address findAddressById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("Address not found"));
	}


	public Address createAddress(AddressDTO data) {
		Address newAddress = new Address(data);
		this.saveAddress(newAddress);
		return newAddress;
	}

	public Address saveNewAddressForUser(String userId, Address address) throws Exception {
		User user = this.userRepository.findUserById(userId).orElseThrow(() -> new Exception("User not found"));
		address.setUser(user);
		return this.repository.save(address);
	}
	
	public Address saveAddress(Address address) {
		return this.repository.save(address);
	}
	
	public List<Address> findAddressByUserId(String id) {
		return this.repository.findAddressByUserId(id);
	}
	
}
