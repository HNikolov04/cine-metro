package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;

import java.util.List;

public interface ITicketService {
    TicketResponseDto createTicket(TicketRequestDto request);
    List<TicketResponseDto> getAllTickets();
    TicketResponseDto getTicketById(Long id);
    void deleteTicket(Long id);
}