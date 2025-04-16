package com.entrego.services;


import java.time.LocalDateTime;
import java.util.List;

import com.entrego.domain.ProductCategory;
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

	@Autowired
	private ProductCategoryService productCategoryService;
	
	public Product findProductById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
	}
	
	public Product createProduct(ProductDTO data) throws Exception {
		Product newProduct = new Product(data);
		System.out.println("Id: " + data.productCategoryId());
		Store store = this.storeRepository.findById(data.storeId()).orElseThrow(() -> new Exception("Store not found"));
		ProductCategory productCategory = this.productCategoryService.findProductCategoryById(data.productCategoryId());
		newProduct.setStore(store);
		newProduct.setProductCategory(productCategory);
		this.saveProduct(newProduct);
		
		return newProduct;
	}

	public List<Product> findProductsByStoreEmail(String email){
		return this.repository.findProductsByStoreEmail(email);
	}
	
	public void saveProduct(Product product) {
		this.repository.save(product);
	}

	public Product updateProduct(String id, ProductDTO data) throws Exception {
		Product product = this.repository.findById(id).orElseThrow(() -> new Exception("Product not found"));
		product.setName(data.name());
		product.setPrice(data.price());
		product.setDiscount(data.discount());
		ProductCategory productCategory = this.productCategoryService.findProductCategoryById(data.productCategoryId());
		product.setProductCategory(productCategory);
		product.setUpdatedAt(LocalDateTime.now());
		return this.repository.save(product);
	}
	
	public List<Product> findAllProducts(){
		return this.repository.findAll();
	}
}
