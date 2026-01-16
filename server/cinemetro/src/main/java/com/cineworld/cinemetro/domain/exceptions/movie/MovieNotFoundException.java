package com.cineworld.cinemetro.domain.exceptions.movie;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Long id) {
        super("Movie with id " + id + " was not found.");
    }
}
