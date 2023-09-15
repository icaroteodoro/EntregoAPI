package com.entrego.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.EnterpriseDTO;
import com.entrego.entity.Address;
import com.entrego.entity.Enterprise;
import com.entrego.repositories.EnterpriseRepository;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository repository;
	
	@Autowired
	private AddressService addressService;
	
	public Enterprise findEnterpriseById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public Enterprise createEnterprise(EnterpriseDTO data) {
		Enterprise newEnterprise = new Enterprise(data);
		Address newAddress = this.addressService.createAddress(data.address());
		newEnterprise.setAddress(newAddress);
		this.saveEnterprise(newEnterprise);
		return newEnterprise;
		
	}
	
	public void saveEnterprise(Enterprise enterprise) {
		this.repository.save(enterprise);
	}
	
	public List<Enterprise> findAllEnterprises(){
		return this.repository.findAll();
	}
}
