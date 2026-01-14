package com.cineworld.cinemetro.application.dto.product;

import com.cineworld.cinemetro.domain.enums.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank
        String name,
        @NotNull
        ProductType type,
        @NotNull
        @Positive
        BigDecimal price,
        String description
) {}
