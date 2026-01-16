package com.cineworld.cinemetro.domain.exceptions.screening;

import java.time.LocalDateTime;

public class ScreeningAlreadyExistsException extends RuntimeException {

    public ScreeningAlreadyExistsException(Long hallId, LocalDateTime startTime) {
        super("Screening already exists in hall with id " + hallId + " at " + startTime + ".");
    }
}
