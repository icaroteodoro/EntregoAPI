package com.entrego.repositories;

import java.util.List;
import java.util.Optional;

import com.entrego.enums.StoreCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.domain.Store;

public interface StoreRepository extends JpaRepository<Store, String> {
	Optional<Store> findStoreByDocument(String document);
	Optional<Store> findStoreByEmail(String email);
	List<Store> findStoresByCategory(StoreCategoryEnum category);
}
