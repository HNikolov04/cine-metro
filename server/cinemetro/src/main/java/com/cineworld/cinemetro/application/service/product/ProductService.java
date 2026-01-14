package com.cineworld.cinemetro.application.service.product;

import com.cineworld.cinemetro.application.dto.product.ProductRequestDto;
import com.cineworld.cinemetro.application.dto.product.ProductResponseDto;
import com.cineworld.cinemetro.application.mapper.product.ProductMapper;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.persistence.repository.product.ProductRepository;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = mapper.toEntity(dto);
        repository.save(product);
        return mapper.toResponse(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return mapper.toResponse(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        existing.setName(dto.name());
        existing.setType(dto.type());
        existing.setPrice(dto.price());
        existing.setDescription(dto.description());

        repository.save(existing);
        return mapper.toResponse(existing);
    }

    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
