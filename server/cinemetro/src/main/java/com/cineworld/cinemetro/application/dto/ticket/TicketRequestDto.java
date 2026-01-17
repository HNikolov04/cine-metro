package com.cineworld.cinemetro.application.dto.ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketRequestDto(
        @NotNull
        @Positive
        Long screeningId,
        @NotNull
        @Positive
        Long seatId
) {}
