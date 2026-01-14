package com.cineworld.cinemetro.domain.model.product;

import com.cineworld.cinemetro.domain.enums.product.ProductType;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type; // e.g. FOOD, DRINK, ACCESSORY

    @Column(nullable = false)
    private BigDecimal price;

    private String description;
}