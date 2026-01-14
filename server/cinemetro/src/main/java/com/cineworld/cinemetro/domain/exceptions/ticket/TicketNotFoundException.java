package com.cineworld.cinemetro.domain.exceptions.ticket;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Ticket not found with ID: " + id);
    }
}
