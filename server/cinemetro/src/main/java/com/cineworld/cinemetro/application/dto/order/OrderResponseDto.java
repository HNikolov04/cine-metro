package com.cineworld.cinemetro.application.dto.order;

import com.cineworld.cinemetro.domain.enums.order.DiscountType;
import com.cineworld.cinemetro.domain.enums.order.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Long userId,
        List<OrderItemResponseDto> tickets,
        List<OrderItemResponseDto> products,
        OrderStatus status,
        DiscountType discountType,
        BigDecimal discountValue,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {}
