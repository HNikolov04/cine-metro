package com.cineworld.cinemetro.domain.exceptions.cinema.seat;

public class SeatNotInHallException extends RuntimeException {

    public SeatNotInHallException(Long seatId, Long hallId) {
        super("Seat " + seatId + " does not belong to hall " + hallId + ".");
    }
}
