package com.cineworld.cinemetro.application.service.ticket;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.mapper.ticket.TicketMapper;
import com.cineworld.cinemetro.persistence.repository.ticket.TicketRepository;
import com.cineworld.cinemetro.domain.model.placeholder.Seat;
import com.cineworld.cinemetro.domain.model.placeholder.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    // NOTE: temporary helpers until full repositories for Seat/Screening exist
    private Screening fakeScreening(Long id) {
        return Screening.builder().id(id).startTime("TEMP").build();
    }

    private Seat fakeSeat(Long id) {
        return Seat.builder().id(id).seatNumber("TEMP").build();
    }

    public TicketResponseDto createTicket(TicketRequestDto request) {
        Screening screening = fakeScreening(request.screeningId());
        Seat seat = fakeSeat(request.seatId());

        Ticket ticket = ticketMapper.toEntity(request, screening, seat);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TicketResponseDto getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
