package com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCinemaBuildingRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String address,
        @NotNull
        @Positive
        Long cityId
) {}
