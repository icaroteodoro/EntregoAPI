package com.entrego.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.entity.Address;
import com.entrego.repositories.AddressRepository;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressRepository repository;
	
	@GetMapping
	public List<Address> findAllAddress() {
		return this.repository.findAll();
	}
	@GetMapping
	@RequestMapping("/{id}")
	public List<Address> findAllAddressByUserId(@PathVariable String id){
		return this.repository.findAddressByUserId(id);
	}
}
