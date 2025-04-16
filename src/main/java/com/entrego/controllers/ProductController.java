package com.entrego.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.domain.ProductCategory;
import com.entrego.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.entrego.dtos.ProductDTO;
import com.entrego.domain.Product;
import com.entrego.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	private ProductCategoryService productCategoryService;
	
	
	@PostMapping
	@RequestMapping("/create")
	public Product saveProduct(@RequestBody ProductDTO data) throws Exception {
		return this.productService.createProduct(data); 
	}
	@GetMapping("/store/{email}")
	public List<Product> findProductsByStoreEmail(@PathVariable String email){
		return this.productService.findProductsByStoreEmail(email);
	}
	
	@GetMapping
	public List<Product> allProducts(){
		return this.productService.findAllProducts();
	}

	@PutMapping
	@RequestMapping("/update/{id}")
	public Product updateProduct(@PathVariable String id, @RequestBody ProductDTO data) throws Exception {
		return this.productService.updateProduct(id, data);
	}


}
