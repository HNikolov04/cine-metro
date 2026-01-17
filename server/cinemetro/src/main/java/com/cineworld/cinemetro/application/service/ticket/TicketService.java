package com.cineworld.cinemetro.application.service.ticket;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.mapper.ticket.TicketMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotInHallException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.persistence.repository.cinema.SeatRepository;
import com.cineworld.cinemetro.persistence.repository.screening.ScreeningRepository;
import com.cineworld.cinemetro.persistence.repository.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public TicketResponseDto createTicket(TicketRequestDto request) {
        Screening screening = screeningRepository.findById(request.screeningId())
                .orElseThrow(() -> new ScreeningNotFoundException(request.screeningId()));
        Seat seat = seatRepository.findById(request.seatId())
                .orElseThrow(() -> new SeatNotFoundException(request.seatId()));

        if (!seat.getHall().getId().equals(screening.getHall().getId())) {
            throw new SeatNotInHallException(request.seatId(), screening.getHall().getId());
        }

        if (ticketRepository.existsByScreening_IdAndSeat_Id(screening.getId(), seat.getId())) {
            throw new TicketAlreadyExistsException(screening.getId(), seat.getId());
        }

        Ticket ticket = ticketMapper.toEntity(screening, seat, screening.getBasePrice());
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponseDto getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(ticketMapper::toResponse)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
