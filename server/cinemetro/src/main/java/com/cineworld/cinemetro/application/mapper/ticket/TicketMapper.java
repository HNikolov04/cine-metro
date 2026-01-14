package com.cineworld.cinemetro.application.mapper.ticket;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.domain.model.placeholder.Seat;
import com.cineworld.cinemetro.domain.model.placeholder.Screening;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketRequestDto request, Screening screening, Seat seat) {
        return Ticket.builder()
                .screening(screening)
                .seat(seat)
                .price(request.price())
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
