package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.service.IProductService;
import com.cineworld.cinemetro.application.dto.product.ProductRequestDto;
import com.cineworld.cinemetro.application.dto.product.ProductResponseDto;
import com.cineworld.cinemetro.application.mapper.ProductMapper;
import com.cineworld.cinemetro.domain.model.product.Product;
import com.cineworld.cinemetro.persistence.repository.ProductRepository;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductService implements IProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = mapper.toEntity(dto);
        repository.save(product);
        return mapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        return mapper.toResponse(product);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setType(dto.getType());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());

        repository.save(existing);
        return mapper.toResponse(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
