package com.cineworld.cinemetro.application.dto.order;

import com.cineworld.cinemetro.domain.model.order.DiscountType;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    private Long userId;                       // Which user created the order
    private List<Long> ticketIds;              // List of Ticket IDs being purchased
    private List<Long> productIds;             // List of Product IDs being purchased

    private DiscountType discountType;         // Default = NONE
    private BigDecimal discountValue;          // % or fixed amount (depending on logic)
}
