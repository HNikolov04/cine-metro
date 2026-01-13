package com.cineworld.cinemetro.webapi.controller;

import com.cineworld.cinemetro.application.dto.order.*;
import com.cineworld.cinemetro.application.dto.ticket.TicketRequestDto;
import com.cineworld.cinemetro.application.dto.ticket.TicketResponseDto;
import com.cineworld.cinemetro.application.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}