package com.entrego.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.entity.Request;
public interface RequestRepository extends JpaRepository<Request, String> {
	
}
