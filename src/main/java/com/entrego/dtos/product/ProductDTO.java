package com.entrego.dtos.product;


import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(String name, BigDecimal price, String productCategoryId, int discount, String storeId, List<ProductOptionGroupDTO> optionGroups, BigDecimal minPrice) {

}
