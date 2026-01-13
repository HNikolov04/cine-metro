package com.cineworld.cinemetro.application.dto.product;

import com.cineworld.cinemetro.domain.model.product.ProductType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private ProductType type;
    private BigDecimal price;
    private String description;
}