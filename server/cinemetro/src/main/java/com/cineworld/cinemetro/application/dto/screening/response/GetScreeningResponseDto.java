package com.cineworld.cinemetro.application.dto.screening.response;

import com.cineworld.cinemetro.domain.enums.screening.ScreeningType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record GetScreeningResponseDto(
        Long id,
        Long movieId,
        Long hallId,
        LocalDateTime startTime,
        ScreeningType screeningType,
        BigDecimal basePrice
) { }
