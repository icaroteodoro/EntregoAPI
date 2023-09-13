package com.entrego.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.RequestDTO;
import com.entrego.entity.Enterprise;
import com.entrego.entity.Product;
import com.entrego.entity.Request;
import com.entrego.entity.User;
import com.entrego.repositories.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private RequestRepository repository;
	private List<Product> products;
	
	public void createRequest(RequestDTO request) throws Exception {
		products = null;
		User user = this.userService.findUserById(request.user().getId());
		Enterprise enterprise = this.enterpriseService.findEnterpriseById(request.enterprise().getId());
		for (int i = 0; i < request.products().size(); i++) {
			Product product = productService.findProductById(request.products().get(i).getId());
			products.add(product);
		}
		
		Request newRequest = new Request();
		newRequest.setUser(user);
		newRequest.setEnterprise(enterprise);
		newRequest.setProdutos(products);
		newRequest.setCreatedAt(LocalDateTime.now());
		newRequest.setUpdatedAt(LocalDateTime.now());
		
		repository.save(newRequest);
	}
}
