package com.entrego.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.AddressDTO;
import com.entrego.entity.Address;
import com.entrego.repositories.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository repository;
	
	
	public Address findAddressById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public Address createAddress(AddressDTO data) {
		Address newAddress = new Address(data);
		this.saveAddress(newAddress);
		return newAddress;
	}
	
	public void saveAddress(Address address) {
		this.repository.save(address);
	}
	
	public List<Address> findAddressByUserId(String id) {
		return this.repository.findAddressByUserId(id);
	}
	
}
