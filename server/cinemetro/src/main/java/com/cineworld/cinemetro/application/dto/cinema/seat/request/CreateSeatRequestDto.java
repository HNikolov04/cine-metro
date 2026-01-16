package com.cineworld.cinemetro.application.dto.cinema.seat.request;

import com.cineworld.cinemetro.domain.enums.cinema.SeatType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateSeatRequestDto(
        @NotNull
        @Positive
        Integer rowNumber,
        @NotNull
        @Positive
        Integer seatNumber,
        @NotNull
        SeatType seatType,
        @NotNull
        Long hallId
) { }
