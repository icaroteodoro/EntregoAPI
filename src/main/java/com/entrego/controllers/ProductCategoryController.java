package com.entrego.controllers;

import com.entrego.domain.ProductCategory;
import com.entrego.dtos.RequestCreateProductCategory;
import com.entrego.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping
    public ProductCategory createProductCategory(@RequestBody RequestCreateProductCategory data) throws Exception {
        return productCategoryService.createCategory(data.name(), data.storeId());
    }
    @GetMapping("/store/{email}")
    public List<ProductCategory> findByStoreEmail(@PathVariable String email){
        return this.productCategoryService.findProductCategoryByStoreEmail(email);
    }

}
