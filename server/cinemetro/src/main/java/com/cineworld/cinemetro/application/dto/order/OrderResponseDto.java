package com.cineworld.cinemetro.application.dto.order;

import com.cineworld.cinemetro.domain.model.order.OrderStatus;
import com.cineworld.cinemetro.domain.model.order.DiscountType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long id;
    private Long userId;

    private List<OrderItemResponseDto> tickets;
    private List<OrderItemResponseDto> products;

    private OrderStatus status;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
