package com.cineworld.cinemetro.application.mapper.product;

import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.application.dto.product.ProductRequestDto;
import com.cineworld.cinemetro.application.dto.product.ProductResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.name())
                .type(dto.type())
                .price(dto.price())
                .description(dto.description())
                .build();
    }

    public ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getPrice(),
                product.getDescription()
        );
    }
}
