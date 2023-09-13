package com.entrego.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.entity.Product;
import com.entrego.repositories.ProductsRepository;

@Service
public class ProductService {

	@Autowired
	private ProductsRepository repository;
	
	
	public Product findProductById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public void saveProduct(Product product) {
		this.repository.save(product);
	}
}
