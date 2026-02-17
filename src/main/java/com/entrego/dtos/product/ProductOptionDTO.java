package com.entrego.dtos.product;

import java.math.BigDecimal;

public record ProductOptionDTO(
    String name,
    String description,
    BigDecimal price,
    Boolean isAvailable
) {}
