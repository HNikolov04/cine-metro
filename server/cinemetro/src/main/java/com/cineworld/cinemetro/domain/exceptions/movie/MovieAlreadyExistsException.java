package com.cineworld.cinemetro.domain.exceptions.movie;

public class MovieAlreadyExistsException extends RuntimeException {

    public MovieAlreadyExistsException(String title) {
        super("Movie with title '" + title + "' already exists.");
    }
}
