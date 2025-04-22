package com.entrego.controllers;

import com.entrego.domain.ProductCategory;
import com.entrego.dtos.RequestCreateProductCategory;
import com.entrego.services.ProductCategoryService;
import com.entrego.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @PostMapping
    @RequestMapping("/create")
    public ProductCategory createProductCategory(@RequestBody RequestCreateProductCategory data) throws Exception {
        return productCategoryService.createCategory(data.name(), data.storeId());
    }

    @DeleteMapping
    @RequestMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteProductCategory(@PathVariable String id) {
        this.productService.deleteAllProductsByCategoryId(id);
        this.productCategoryService.deleteProductCategoryById(id);
        return ResponseEntity.ok("Successfully deleted");
    }


    @GetMapping("/store/{email}")
    public List<ProductCategory> findProductCategoryByStoreEmail(@PathVariable String email){
        return this.productCategoryService.findProductCategoryByStoreEmail(email);
    }

}
