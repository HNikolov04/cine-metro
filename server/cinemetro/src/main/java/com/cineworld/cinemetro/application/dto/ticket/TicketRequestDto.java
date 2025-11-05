package com.cineworld.cinemetro.application.dto.ticket;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequestDto {
    private Long screeningId;
    private Long seatId;
    private BigDecimal price;
}