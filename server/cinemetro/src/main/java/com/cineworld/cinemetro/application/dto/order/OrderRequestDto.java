package com.cineworld.cinemetro.application.dto.order;

import com.cineworld.cinemetro.domain.enums.order.DiscountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
        @NotNull
        @Positive
        Long userId,
        @NotNull
        List<Long> ticketIds,
        @NotNull
        List<Long> productIds,
        DiscountType discountType,
        @PositiveOrZero
        BigDecimal discountValue
) {}
