package com.cineworld.cinemetro.domain.exceptions.ticket;

public class TicketAlreadyExistsException extends RuntimeException {

    public TicketAlreadyExistsException(Long screeningId, Long seatId) {
        super("Seat " + seatId + " is already booked for screening " + screeningId + ".");
    }
}
