package com.entrego.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entrego.domain.Address;

public interface AddressRepository extends JpaRepository<Address, String> {
	@Query(nativeQuery = true, value = "SELECT * FROM address WHERE user_id = :id")
	List<Address> findAddressByUserId(String id);
}
