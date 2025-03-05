package com.entrego.controllers;

import java.util.List;

import com.entrego.dtos.RegisterStoreRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.StoreDTO;
import com.entrego.domain.Store;
import com.entrego.services.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	
	@PostMapping
	private Store saveStore(@RequestBody RegisterStoreRequestDTO data) {
		return storeService.createStore(data);
	}
	
	@GetMapping
	private List<Store> allStores(){
		return this.storeService.findAllStores();
	}
	
	@GetMapping
	@RequestMapping("/{id}")
	public Store findStoreById(@PathVariable String id) throws Exception {
		return this.storeService.findStoreById(id);
	}
	

}
