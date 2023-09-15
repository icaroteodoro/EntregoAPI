package com.entrego.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Request;
import com.entrego.repositories.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private RequestRepository repository;
	
	
	
	public Request createRequest(RequestDTO request) {
		Request newRequest = new Request(request);
		System.out.println(newRequest.getProdutos().get(0).getName());
		
		this.saveRequest(newRequest);
		return newRequest;
	}
	
	public void saveRequest(Request request) {
		this.repository.save(request);
	}
	
	public List<Request> findRequestsByUserId(String id){
		return this.repository.findRequestsByUserId(id);
	}
	public List<Request> findRequestsByEnterpriseId(String id){
		return this.repository.findRequestsByEnterpriseId(id);
	}
}
