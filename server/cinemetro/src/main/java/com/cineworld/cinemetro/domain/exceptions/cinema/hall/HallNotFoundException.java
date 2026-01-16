package com.cineworld.cinemetro.domain.exceptions.cinema.hall;

public class HallNotFoundException extends RuntimeException {

    public HallNotFoundException(Long id) {
        super("Hall with id " + id + " was not found.");
    }
}
