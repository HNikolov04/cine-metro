package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.mapper.TicketMapper;
import com.cineworld.cinemetro.persistence.repository.TicketRepository;
import com.cineworld.cinemetro.domain.model.placeholder.Seat;
import com.cineworld.cinemetro.domain.model.placeholder.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    // NOTE: temporary helpers until full repositories for Seat/Screening exist
    private Screening fakeScreening(Long id) {
        return Screening.builder().id(id).startTime("TEMP").build();
    }

    private Seat fakeSeat(Long id) {
        return Seat.builder().id(id).seatNumber("TEMP").build();
    }

    @Override
    public TicketResponseDto createTicket(TicketRequestDto request) {
        Screening screening = fakeScreening(request.getScreeningId());
        Seat seat = fakeSeat(request.getSeatId());

        Ticket ticket = ticketMapper.toEntity(request, screening, seat);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    @Override
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TicketResponseDto getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}