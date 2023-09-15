package com.entrego.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Request;
import com.entrego.services.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
	

	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public Request createRequest(@RequestBody RequestDTO data){
		Request newRequest = new Request(data);
		this.requestService.createRequest(data);
		return newRequest;
	}
	
	@GetMapping
	public List<Request> findAllRequests(){
		return this.findAllRequests();
	}
	
	
	@GetMapping
	@RequestMapping("/user/{id}")
	public List<Request> findRequestsByUserId(@PathVariable String id){
		return requestService.findRequestsByUserId(id);
	}
	
	@GetMapping
	@RequestMapping("/enterprise/{id}")
	public List<Request> findRequestsByEnterpriseId(@PathVariable String id){
		return requestService.findRequestsByEnterpriseId(id);
	}

	
}
