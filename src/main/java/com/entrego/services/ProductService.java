package com.entrego.services;


import java.util.List;

import com.entrego.entity.Enterprise;
import com.entrego.repositories.EnterpriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.ProductDTO;
import com.entrego.entity.Product;
import com.entrego.repositories.ProductsRepository;

@Service
public class ProductService {

	@Autowired
	private ProductsRepository repository;

	@Autowired
	private EnterpriseRepository enterpriseRepository;
	
	public Product findProductById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public Product createProduct(ProductDTO data) throws Exception {
		Product newProduct = new Product(data);
		Enterprise enterprise = this.enterpriseRepository.findById(data.enterpriseId()).orElseThrow(() -> new Exception("Enterprise not found"));

		newProduct.setEnterprise(enterprise);

		this.saveProduct(newProduct);
		
		return newProduct;
	}
	
	public void saveProduct(Product product) {
		this.repository.save(product);
	}
	
	public List<Product> findAllProducts(){
		return this.repository.findAll();
	}
}
