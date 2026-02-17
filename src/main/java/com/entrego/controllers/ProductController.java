package com.entrego.controllers;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.domain.ProductCategory;
import com.entrego.services.ProductCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.entrego.dtos.product.ProductDTO;
import com.entrego.domain.Product;
import com.entrego.services.ProductService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	private ProductCategoryService productCategoryService;


	@PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@RequestMapping("/create")
	public Product saveProduct(@RequestParam("data") String data, @RequestParam("file") MultipartFile file) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ProductDTO productDTO = objectMapper.readValue(data, ProductDTO.class);
		return this.productService.createProduct(productDTO, file);
	}

	@PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@RequestMapping("/update-image/{productId}")
	public Product updateProductImage(@PathVariable String productId, @RequestParam("file") MultipartFile file) throws Exception {
		return this.productService.updateProductImage(productId, file);
	}

	@DeleteMapping
	@RequestMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) throws Exception {
		this.productService.deleteProductById(id);
		return ResponseEntity.ok("Successfully deletion");
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
