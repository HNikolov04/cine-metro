package com.cineworld.cinemetro.application.dto.ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TicketRequestDto(
        @NotNull
        @Positive
        Long screeningId,
        @NotNull
        @Positive
        Long seatId,
        @NotNull
        @Positive
        BigDecimal price
) {}
