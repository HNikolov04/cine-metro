package com.cineworld.cinemetro;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.mapper.ticket.TicketMapper;
import com.cineworld.cinemetro.application.service.ticket.TicketService;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.Hall;
import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.persistence.repository.cinema.SeatRepository;
import com.cineworld.cinemetro.persistence.repository.screening.ScreeningRepository;
import com.cineworld.cinemetro.persistence.repository.ticket.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketRequestDto ticketRequest;
    private TicketResponseDto ticketResponse;
    private Screening screening;
    private Seat seat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Hall hall = Hall.builder().id(1L).build();
        screening = Screening.builder().id(1L).hall(hall).startTime(LocalDateTime.now()).build();
        seat = Seat.builder().id(1L).hall(hall).rowNumber(1).seatNumber(1).build();

        ticket = Ticket.builder()
                .id(1L)
                .screening(screening)
                .seat(seat)
                .price(BigDecimal.valueOf(10.0))
                .build();

        ticketRequest = new TicketRequestDto(1L, 1L);
        ticketResponse = new TicketResponseDto(1L, 1L, 1L, BigDecimal.valueOf(10.0));
    }

    @Test
    void createTicket_ShouldReturnSavedTicketResponse() {
        when(screeningRepository.findById(1L)).thenReturn(Optional.of(screening));
        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));
        when(ticketRepository.existsByScreening_IdAndSeat_Id(1L, 1L)).thenReturn(false);
        when(ticketMapper.toEntity(screening, seat, screening.getBasePrice())).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toResponse(any(Ticket.class))).thenReturn(ticketResponse);

        TicketResponseDto result = ticketService.createTicket(ticketRequest);

        assertNotNull(result);
        assertEquals(ticketResponse.id(), result.id());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getAllTickets_ShouldReturnListOfResponses() {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));
        when(ticketMapper.toResponse(any(Ticket.class))).thenReturn(ticketResponse);

        List<TicketResponseDto> results = ticketService.getAllTickets();

        assertEquals(1, results.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void getTicketById_ShouldReturnTicket_WhenFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketMapper.toResponse(ticket)).thenReturn(ticketResponse);

        TicketResponseDto result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void getTicketById_ShouldThrowException_WhenNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById(1L));
        assertTrue(ex.getMessage().contains("Ticket not found"));
    }

    @Test
    void deleteTicket_ShouldCallRepositoryDelete() {
        doNothing().when(ticketRepository).deleteById(1L);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }
}
