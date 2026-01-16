package com.cineworld.cinemetro.application.dto.cinema.seat.response;

import com.cineworld.cinemetro.domain.enums.cinema.SeatType;

public record GetSeatResponseDto(
        Long id,
        Integer rowNumber,
        Integer seatNumber,
        SeatType seatType,
        Long hallId
) { }
