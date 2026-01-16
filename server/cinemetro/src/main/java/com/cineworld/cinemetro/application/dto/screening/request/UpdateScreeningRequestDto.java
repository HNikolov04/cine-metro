package com.cineworld.cinemetro.application.dto.screening.request;

import com.cineworld.cinemetro.domain.enums.screening.ScreeningType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateScreeningRequestDto(
        @NotNull
        Long movieId,
        @NotNull
        Long hallId,
        @NotNull
        LocalDateTime startTime,
        @NotNull
        ScreeningType screeningType,
        @NotNull
        @Positive
        BigDecimal basePrice
) { }
