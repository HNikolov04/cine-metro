package com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding;

public class CinemaBuildingNotFoundException extends RuntimeException {

    public CinemaBuildingNotFoundException(Long id) {
        super("Cinema building with id " + id + " was not found.");
    }

    public CinemaBuildingNotFoundException(String message) {
        super(message);
    }
}
