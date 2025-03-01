package com.entrego.services;



import java.util.List;

import com.entrego.entity.Enterprise;
import com.entrego.entity.User;
import com.entrego.enums.OrderStatus;
import com.entrego.repositories.EnterpriseRepository;
import com.entrego.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Request;
import com.entrego.repositories.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private RequestRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EnterpriseRepository enterpriseRepository;


	public Request createRequest(RequestDTO request) throws Exception {
		Request newRequest = new Request(request);

		User user = this.userRepository.findUserById(request.userId()).orElseThrow(() -> new Exception("User not found"));
		Enterprise enterprise = this.enterpriseRepository.findById(request.enterpriseId()).orElseThrow(() -> new Exception("Enterprise not found"));

		newRequest.setUser(user);
		newRequest.setEnterprise(enterprise);

		double total = 0.0;

		for (int i = 0; i < newRequest.getProducts().size(); i++) {
			total = total + newRequest.getProducts().get(i).getPrice();
		}

		newRequest.setTotal(total);

		this.repository.save(newRequest);
		return newRequest;
	}


	public Request updateStatusByRequestId(String requestId, OrderStatus status) {
		Request request = this.repository.getReferenceById(requestId);
		request.setStatus(status);
		return this.repository.save(request);
	}

	
	public List<Request> findRequestsByUserId(String id){
		return this.repository.findRequestsByUserId(id);
	}
	public List<Request> findRequestsByEnterpriseId(String id){
		return this.repository.findRequestsByEnterpriseId(id);
	}
}
