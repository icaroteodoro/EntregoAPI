package com.entrego.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.domain.Store;

public interface StoreRepository extends JpaRepository<Store, String> {
	Optional<Store> findStoreByDocument(String document);
	Optional<Store> findStoreByEmail(String email);
}
