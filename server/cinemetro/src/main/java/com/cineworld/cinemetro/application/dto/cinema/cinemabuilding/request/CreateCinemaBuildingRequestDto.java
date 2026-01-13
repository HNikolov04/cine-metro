package com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCinemaBuildingRequestDto(
        @NotBlank String name,
        @NotBlank String address,
        @NotNull Long cityId
) { }
