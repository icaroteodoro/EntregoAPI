package com.entrego.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.EnterpriseDTO;
import com.entrego.entity.Enterprise;
import com.entrego.services.EnterpriseService;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {
	@Autowired
	private EnterpriseService enterpriseService;
	
	
	@PostMapping
	private Enterprise saveEnterprise(@RequestBody EnterpriseDTO data) {
		return enterpriseService.createEnterprise(data);
	}
	
	@GetMapping
	private List<Enterprise> allEnterprises(){
		return this.enterpriseService.findAllEnterprises();
	}
	
	@GetMapping
	@RequestMapping("/{id}")
	public Enterprise findEnterpriseById(@PathVariable String id) throws Exception {
		return this.enterpriseService.findEnterpriseById(id);
	}
	

}
