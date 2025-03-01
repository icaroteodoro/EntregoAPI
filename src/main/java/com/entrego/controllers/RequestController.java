package com.entrego.controllers;

import java.util.List;

import com.entrego.dtos.RequestUpdateStatusOfRequest;
import com.entrego.enums.OrderStatus;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Request;
import com.entrego.services.RequestService;

@RestController
@RequestMapping("/request")
public class RequestController {
	

	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public Request createRequest(@RequestBody RequestDTO data) throws Exception {
        return this.requestService.createRequest(data);
	}
	
	
	@GetMapping
	@RequestMapping("/user/{userId}")
	public List<Request> findRequestsByUserId(@PathVariable String userId){
		return requestService.findRequestsByUserId(userId);
	}
	
	@GetMapping
	@RequestMapping("/enterprise/{enterpriseId}")
	public List<Request> findRequestsByEnterpriseId(@PathVariable String enterpriseId){
		return requestService.findRequestsByEnterpriseId(enterpriseId);
	}

	@PutMapping
	@RequestMapping("/update-status/{requestId}")
	public Request updateStatusByRequestId (@PathVariable String requestId, @RequestBody RequestUpdateStatusOfRequest data) {
		return this.requestService.updateStatusByRequestId(requestId, OrderStatus.fromValue(data.status()));
	}


	
}
