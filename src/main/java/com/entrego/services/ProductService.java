package com.entrego.services;


import java.util.List;

import com.entrego.domain.Store;
import com.entrego.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.ProductDTO;
import com.entrego.domain.Product;
import com.entrego.repositories.ProductsRepository;

@Service
public class ProductService {

	@Autowired
	private ProductsRepository repository;

	@Autowired
	private StoreRepository storeRepository;
	
	public Product findProductById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public Product createProduct(ProductDTO data) throws Exception {
		Product newProduct = new Product(data);
		Store store = this.storeRepository.findById(data.storeId()).orElseThrow(() -> new Exception("Store not found"));

		newProduct.setStore(store);

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
