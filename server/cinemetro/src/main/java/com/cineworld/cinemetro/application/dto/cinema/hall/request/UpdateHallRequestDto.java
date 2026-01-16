package com.cineworld.cinemetro.application.dto.cinema.hall.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateHallRequestDto(
        @NotBlank
        @Size(max = 120)
        String name,
        @NotNull
        @Positive
        Integer capacity,
        @NotNull
        Long buildingId
) { }
