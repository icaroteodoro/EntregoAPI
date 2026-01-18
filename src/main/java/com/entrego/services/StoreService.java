package com.entrego.services;

import java.util.List;
import java.util.Objects;

import com.entrego.domain.Store;
import com.entrego.dtos.*;
import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;
import com.entrego.infra.storage.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entrego.domain.Address;
import com.entrego.repositories.StoreRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StoreService {
	@Autowired
	private StoreRepository repository;
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private com.entrego.repositories.AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private FirebaseService firebaseService;

	public Store findStoreById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Store not found"));
	}

	public Store findStoreByEmail(String email) throws Exception {
		var account = this.accountRepository.findAccountByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));
		return this.repository.findStoreByAccount(account).orElseThrow(() -> new RuntimeException("Store not found"));
	}

	public Store createStore(RegisterStoreRequestDTO data) {
		if(this.accountRepository.findAccountByEmail(data.email()).isPresent()) throw new RuntimeException("Email already exists");

		com.entrego.domain.Account newAccount = new com.entrego.domain.Account(data.email(), passwordEncoder.encode(data.password()), "STORE");
		this.accountRepository.save(newAccount);

		Store newStore = new Store(data, newAccount);
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

	public String updateStoreProfileImage(String storeEmail, MultipartFile file) throws Exception {
		Store store = this.findStoreByEmail(storeEmail);

		if(store.getUrlProfileImage() == null){
			String url = this.firebaseService.uploadStoreProfileImage(file,store.getName());
			store.setUrlProfileImage(url);
			this.repository.save(store);

			return url;
		}

		this.firebaseService.deleteImage(store.getUrlProfileImage());

		String url = this.firebaseService.uploadStoreProfileImage(file,store.getName());
		store.setUrlProfileImage(url);
		this.repository.save(store);

		return url;

	}

	public String updateStoreCoverImage(String storeEmail, MultipartFile file) throws Exception {
		Store store = this.findStoreByEmail(storeEmail);
		if(store.getUrlCoverImage() == null){
			String url = this.firebaseService.uploadStoreCoverImage(file, store.getName());
			store.setUrlCoverImage(url);
			this.repository.save(store);

			return url;
		}
		this.firebaseService.deleteImage(store.getUrlCoverImage());
		String url = this.firebaseService.uploadStoreCoverImage(file, store.getName());
		store.setUrlCoverImage(url);
		this.repository.save(store);

		return url;
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
