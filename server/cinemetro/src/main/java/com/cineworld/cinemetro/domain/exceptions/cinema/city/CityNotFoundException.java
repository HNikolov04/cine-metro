package com.cineworld.cinemetro.domain.exceptions.cinema.city;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Long id) {
        super("City with id " + id + " was not found.");
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
