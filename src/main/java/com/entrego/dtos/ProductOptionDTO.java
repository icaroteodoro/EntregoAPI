package com.entrego.dtos;

import java.math.BigDecimal;

public record ProductOptionDTO(
    String name,
    String description,
    BigDecimal price,
    Boolean isAvailable
) {}
