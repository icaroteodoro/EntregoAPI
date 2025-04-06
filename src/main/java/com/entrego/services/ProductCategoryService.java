package com.entrego.services;

import com.entrego.domain.ProductCategory;
import com.entrego.domain.Store;
import com.entrego.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private StoreService storeService;

    public ProductCategory createCategory(String name, String storeId) throws Exception {
        ProductCategory newProductCategory = new ProductCategory();
        Store store = this.storeService.findStoreById(storeId);
        newProductCategory.setName(name);
        newProductCategory.setStore(store);
        productCategoryRepository.save(newProductCategory);
        return newProductCategory;
    }

    public ProductCategory findProductCategoryById(String productCategoryId) throws Exception {
        return this.productCategoryRepository.findById(productCategoryId).orElseThrow(() -> new Exception("Category not found"));
    }

    public List<ProductCategory> findProductCategoryByStoreEmail(String email) {
        return this.productCategoryRepository.findProductCategoriesByStoreEmail(email);
    }


}
