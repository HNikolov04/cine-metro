package com.cineworld.cinemetro.application.dto.order;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long id;
    private String name;       // product name or movie title
    private String type;       // "PRODUCT" or "TICKET"
    private BigDecimal price;
}

