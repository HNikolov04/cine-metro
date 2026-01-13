package com.cineworld.cinemetro.webapi.globalexception;

import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityHasBuildingsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFound(CityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<String> handleCityAlreadyExists(CityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CityHasBuildingsException.class)
    public ResponseEntity<String> handleCityHasBuildings(CityHasBuildingsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CinemaBuildingNotFoundException.class)
    public ResponseEntity<String> handleCinemaBuildingNotFound(CinemaBuildingNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CinemaBuildingAlreadyExistsException.class)
    public ResponseEntity<String> handleCinemaBuildingAlreadyExists(CinemaBuildingAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
