package com.cineworld.cinemetro;

import java.math.BigDecimal;
import com.cineworld.cinemetro.application.service.TicketService;
import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.mapper.TicketMapper;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.persistence.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService; // Service under test

    private Ticket ticket;
    private TicketRequestDto ticketRequest;
    private TicketResponseDto ticketResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ticket = Ticket.builder()
                .id(1L)
                .price(BigDecimal.valueOf(10.0))
                .build();

        ticketRequest = new TicketRequestDto();
        ticketRequest.setSeatId(1L);
        ticketRequest.setScreeningId(1L);

        ticketResponse = new TicketResponseDto();
        ticketResponse.setId(1L);
        ticketResponse.setPrice(BigDecimal.valueOf(10.0));
    }

    @Test
    void createTicket_ShouldReturnSavedTicketResponse() {
        // Arrange
        when(ticketMapper.toEntity(any(), any(), any())).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toResponse(any(Ticket.class))).thenReturn(ticketResponse);

        // Act
        TicketResponseDto result = ticketService.createTicket(ticketRequest);

        // Assert
        assertNotNull(result);
        assertEquals(ticketResponse.getId(), result.getId());
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
        assertEquals(1L, result.getId());
    }

    @Test
    void getTicketById_ShouldThrowException_WhenNotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> ticketService.getTicketById(1L));
        assertTrue(ex.getMessage().contains("Ticket not found"));
    }

    @Test
    void deleteTicket_ShouldCallRepositoryDelete() {
        doNothing().when(ticketRepository).deleteById(1L);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }
}