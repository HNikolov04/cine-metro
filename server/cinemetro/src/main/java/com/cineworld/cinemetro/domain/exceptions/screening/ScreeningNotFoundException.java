package com.cineworld.cinemetro.domain.exceptions.screening;

public class ScreeningNotFoundException extends RuntimeException {

    public ScreeningNotFoundException(Long id) {
        super("Screening with id " + id + " was not found.");
    }
}
