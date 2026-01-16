package com.cineworld.cinemetro.domain.exceptions.cinema.hall;

public class HallAlreadyExistsException extends RuntimeException {

    public HallAlreadyExistsException(Long buildingId, String name) {
        super("Hall with name '" + name + "' already exists in building with id " + buildingId + ".");
    }
}
