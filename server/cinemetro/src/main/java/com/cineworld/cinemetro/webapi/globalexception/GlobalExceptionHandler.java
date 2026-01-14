package com.cineworld.cinemetro.webapi.globalexception;

import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityHasBuildingsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.order.OrderNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFound(CityNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCityAlreadyExists(CityAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CityHasBuildingsException.class)
    public ResponseEntity<ErrorResponse> handleCityHasBuildings(CityHasBuildingsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CinemaBuildingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCinemaBuildingNotFound(CinemaBuildingNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CinemaBuildingAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCinemaBuildingAlreadyExists(CinemaBuildingAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFound(TicketNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
        return ResponseEntity.status(status).body(response);
    }

    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message
    ) { }
}
