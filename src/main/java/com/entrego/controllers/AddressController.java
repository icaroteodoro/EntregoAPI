package com.entrego.controllers;

import java.util.List;

import com.entrego.dtos.AddressDTO;
import com.entrego.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.entrego.domain.Address;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	


	@GetMapping
	public List<Address> findAllAddress() {
		return this.addressService.findAllAddress();
	}

	@PostMapping
	@RequestMapping("/save-new-address-for-user/{userId}")
	public Address saveNewAddressForUser(@PathVariable String userId, @RequestBody AddressDTO data) throws Exception {
		Address newAddress = new Address(data);
		return this.addressService.saveNewAddressForUser(userId, newAddress);
	}

	@GetMapping
	@RequestMapping("/{userId}")
	public List<Address> findAllAddressByUserId(@PathVariable String userId){
		return this.addressService.findAddressByUserId(userId);
	}
}
