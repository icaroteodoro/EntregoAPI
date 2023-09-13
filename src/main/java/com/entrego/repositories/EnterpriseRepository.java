package com.entrego.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.entity.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, String> {
	Optional<Enterprise> findEnterpriseByDocument(String document);
	Optional<Enterprise> findEnterpriseByEmail(String email);
}
