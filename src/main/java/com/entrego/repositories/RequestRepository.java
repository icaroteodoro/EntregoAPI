package com.entrego.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entrego.entity.Request;
public interface RequestRepository extends JpaRepository<Request, String> {
	List<Request> findRequestsByUserId(String id);
	List<Request> findRequestsByEnterpriseId(String id);
}
