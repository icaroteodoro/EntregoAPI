package com.entrego.controllers;

import java.util.List;

import com.entrego.dtos.address.AddressDTO;
import com.entrego.dtos.store.request.AddressStoreDTO;
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

	@PutMapping
	@RequestMapping("/store/update/{id}")
	public Address updateStoreAddressByStoreEmail(@PathVariable String id, @RequestBody AddressStoreDTO data) throws Exception {
		System.out.println(data.street());
		return this.addressService.updateAddressById(id, data);
	}
}
