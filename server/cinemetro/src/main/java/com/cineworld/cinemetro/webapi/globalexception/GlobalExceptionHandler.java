package com.cineworld.cinemetro.webapi.globalexception;

import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityHasBuildingsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotInHallException;
import com.cineworld.cinemetro.domain.exceptions.movie.MovieAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.movie.MovieNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.notification.NotificationNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.order.OrderNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.order.OrderValidationException;
import com.cineworld.cinemetro.domain.exceptions.product.ProductNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningTimeConflictException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.ticket.TicketNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.user.InvalidCredentialsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
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

    @ExceptionHandler(HallNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHallNotFound(HallNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HallAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleHallAlreadyExists(HallAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSeatNotFound(SeatNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SeatAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadyExists(SeatAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SeatNotInHallException.class)
    public ResponseEntity<ErrorResponse> handleSeatNotInHall(SeatNotInHallException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleMovieAlreadyExists(MovieAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ScreeningNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScreeningNotFound(ScreeningNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ScreeningAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleScreeningAlreadyExists(ScreeningAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ScreeningTimeConflictException.class)
    public ResponseEntity<ErrorResponse> handleScreeningTimeConflict(ScreeningTimeConflictException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<ErrorResponse> handleOrderValidation(OrderValidationException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFound(TicketNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(TicketAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTicketAlreadyExists(TicketAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFound(NotificationNotFoundException ex) {
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
