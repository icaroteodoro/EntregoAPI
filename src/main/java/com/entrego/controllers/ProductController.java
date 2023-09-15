package com.entrego.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entrego.dtos.ProductDTO;
import com.entrego.entity.Product;
import com.entrego.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	
	@PostMapping
	public Product saveProduct(@RequestBody ProductDTO data) {
		return this.productService.createProduct(data); 
	}
	
	@GetMapping
	public List<Product> allProducts(){
		return this.productService.findAllProducts();
	}

}
