package com.cineworld.cinemetro.domain.exceptions.cinema.city;

public class CityHasBuildingsException extends RuntimeException {

    public CityHasBuildingsException(Long id) {
        super("City with id " + id + " cannot be deleted because it has buildings.");
    }
}
