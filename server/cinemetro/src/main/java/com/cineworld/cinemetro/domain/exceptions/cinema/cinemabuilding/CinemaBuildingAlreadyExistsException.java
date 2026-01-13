package com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding;

public class CinemaBuildingAlreadyExistsException extends RuntimeException {

    public CinemaBuildingAlreadyExistsException(Long cityId, String name) {
        super("Cinema building with name '" + name + "' already exists in city with id " + cityId + ".");
    }
}