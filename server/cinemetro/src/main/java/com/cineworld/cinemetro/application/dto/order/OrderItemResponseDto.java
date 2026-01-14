package com.cineworld.cinemetro.application.dto.order;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        String name,
        String type,
        BigDecimal price
) {}

