package com.entrego.dtos.product;

import java.util.List;

public record ProductOptionGroupDTO(
    String name,
    Integer minSelection,
    Integer maxSelection,
    List<ProductOptionDTO> options
) {}
