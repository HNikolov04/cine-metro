package com.cineworld.cinemetro.application.dto.ticket;

import java.math.BigDecimal;

public record TicketResponseDto(
        Long id,
        Long screeningId,
        Long seatId,
        BigDecimal price
) {}
