package com.entrego.services;

import java.util.List;

import com.entrego.domain.Store;
import com.entrego.dtos.RegisterStoreRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entrego.dtos.StoreDTO;
import com.entrego.domain.Address;
import com.entrego.repositories.StoreRepository;

@Service
public class StoreService {
	@Autowired
	private StoreRepository repository;
	
	@Autowired
	private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

	public Store findStoreById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
	}

	public Store findStoreByEmail(String email) throws Exception {
		return this.repository.findStoreByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));
	}

	public Store createStore(RegisterStoreRequestDTO data) {
		Store newStore = new Store(data);
		newStore.setPassword(passwordEncoder.encode(data.password()));
		Address newAddress = this.addressService.createAddress(data.address());
		newStore.setAddress(newAddress);
		this.saveEnterprise(newStore);
		return newStore;
		
	}
	
	public void saveEnterprise(Store store) {
		this.repository.save(store);
	}
	
	public List<Store> findAllStores(){
		return this.repository.findAll();
	}
}
