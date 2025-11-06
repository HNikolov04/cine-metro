package com.cineworld.cinemetro.application.dto.cinema.city.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCityRequestDto(
        @NotBlank String name
) { }
