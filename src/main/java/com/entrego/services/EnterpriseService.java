package com.entrego.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.entity.Enterprise;
import com.entrego.repositories.EnterpriseRepository;

@Service
public class EnterpriseService {
	@Autowired
	private EnterpriseRepository repository;
	
	public Enterprise findEnterpriseById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public void saveEnterprise(Enterprise enterprise) {
		this.repository.save(enterprise);
	}
}
