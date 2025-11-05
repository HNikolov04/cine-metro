package com.cineworld.cinemetro.application.mapper;

import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.application.dto.product.ProductRequestDto;
import com.cineworld.cinemetro.application.dto.product.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .type(dto.getType())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }

    public ProductResponseDto toResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}