package com.entrego.services;

import java.util.List;
import java.util.Objects;

import com.entrego.domain.Store;
import com.entrego.dtos.*;
import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		if(!Objects.equals(data.address().city(), "")) {
			Address newAddress = this.addressService.createAddress(data.address());
			newStore.setAddress(newAddress);
		}
		System.out.println(data.category());
		this.saveEnterprise(newStore);
		return newStore;
	}

	public Store updateStore(String email, RequestUpdateStore data) throws Exception {
		Store store = this.findStoreByEmail(email);
		store.setName(data.name());
		store.setDescription(data.description());
		store.setCategory(data.category());

		return this.repository.save(store);
	}


	public Store updateStoreStatus(RequestUpdateStoreStatus data) throws Exception {
		Store store = this.findStoreByEmail(data.email());
		store.setStatusLive(StoreStatus.fromValue(data.status()));
		this.repository.save(store);
		return store;
	}
	
	public void saveEnterprise(Store store) {
		this.repository.save(store);
	}
	
	public List<Store> findAllStores(){
		return this.repository.findAll();
	}

	public List<Store> findStoresByCategory(String category){
		return this.repository.findStoresByCategory(StoreCategoryEnum.fromValue(category));
	}

	public Address findAddressByStoreEmail(String email) throws Exception {
		Store store = this.findStoreByEmail(email);
		return store.getAddress();
	}



}
