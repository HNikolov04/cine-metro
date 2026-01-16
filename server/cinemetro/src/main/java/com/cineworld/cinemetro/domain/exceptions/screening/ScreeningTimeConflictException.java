package com.cineworld.cinemetro.domain.exceptions.screening;

import java.time.LocalDateTime;

public class ScreeningTimeConflictException extends RuntimeException {

    public ScreeningTimeConflictException(Long hallId, LocalDateTime startTime) {
        super("Screening time conflicts in hall " + hallId + " at " + startTime + ".");
    }
}
