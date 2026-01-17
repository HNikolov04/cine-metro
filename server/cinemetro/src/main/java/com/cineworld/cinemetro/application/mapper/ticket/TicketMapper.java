package com.cineworld.cinemetro.application.mapper.ticket;

import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TicketMapper {

    public Ticket toEntity(Screening screening, Seat seat, BigDecimal price) {
        return Ticket.builder()
                .screening(screening)
                .seat(seat)
                .price(price)
                .build();
    }

    public TicketResponseDto toResponse(Ticket ticket) {
        return new TicketResponseDto(
                ticket.getId(),
                ticket.getScreening().getId(),
                ticket.getSeat().getId(),
                ticket.getPrice()
        );
    }
}
