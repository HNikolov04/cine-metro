package com.cineworld.cinemetro.domain.exceptions.cinema.city;

public class CityAlreadyExistsException extends RuntimeException {

    public CityAlreadyExistsException(String name) {
        super("City with name '" + name + "' already exists.");
    }
}