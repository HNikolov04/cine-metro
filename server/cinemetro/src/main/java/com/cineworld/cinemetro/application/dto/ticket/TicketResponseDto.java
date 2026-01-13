package com.cineworld.cinemetro.application.dto.ticket;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private Long id;
    private Long screeningId;
    private Long seatId;
    private BigDecimal price;
}