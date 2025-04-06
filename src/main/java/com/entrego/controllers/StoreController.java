package com.entrego.controllers;

import java.util.List;

import com.entrego.domain.Address;
import com.entrego.dtos.*;
import com.entrego.enums.StoreCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	
	//@GetMapping
	//@RequestMapping("/{id}")
	//public Store findStoreById(@PathVariable String id) throws Exception {
	//	return this.storeService.findStoreById(id);
	//}
	@GetMapping
	@RequestMapping("/category")
	public List<Store> findStoresByCategory(@RequestBody RequestStoreByCategory data) {
		return storeService.findStoresByCategory(data.category());
	}

	@PutMapping
	@RequestMapping("/update-status")
	public Store updateStoreStatus(@RequestBody RequestUpdateStoreStatus data) throws Exception {
		return storeService.updateStoreStatus(data);
	}

	@PutMapping
	@RequestMapping("/update/{email}")
	public Store updateStore(@PathVariable String email, @RequestBody RequestUpdateStore data) throws Exception {
		return this.storeService.updateStore(email, data);
	}


	@GetMapping
	@RequestMapping("/{email}")
	public Store findStoreByEmail(@PathVariable String email) throws Exception {
		return this.storeService.findStoreByEmail(email);
	}

	@GetMapping
	@RequestMapping("/address/{email}")
	public Address findAddressByStoreEmail(@PathVariable String email) throws Exception {
		return this.storeService.findAddressByStoreEmail(email);
	}




}
