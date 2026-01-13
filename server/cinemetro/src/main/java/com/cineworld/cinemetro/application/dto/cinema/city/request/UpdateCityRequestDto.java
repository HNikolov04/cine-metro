package com.cineworld.cinemetro.application.dto.cinema.city.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCityRequestDto(
        @NotBlank
        @Size(max = 120)
        String name
) { }
