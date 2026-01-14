package com.cineworld.cinemetro.application.dto.product;

import com.cineworld.cinemetro.domain.enums.product.ProductType;
import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        ProductType type,
        BigDecimal price,
        String description
) {}
