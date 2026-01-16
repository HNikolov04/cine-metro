package com.cineworld.cinemetro.domain.exceptions.cinema.seat;

public class SeatAlreadyExistsException extends RuntimeException {

    public SeatAlreadyExistsException(Long hallId, Integer rowNumber, Integer seatNumber) {
        super("Seat " + rowNumber + "-" + seatNumber + " already exists in hall with id " + hallId + ".");
    }
}
