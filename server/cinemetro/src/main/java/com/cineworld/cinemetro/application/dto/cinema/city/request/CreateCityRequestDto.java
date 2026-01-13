package com.cineworld.cinemetro.application.dto.cinema.city.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCityRequestDto(
        @NotBlank
        @Size(max = 120)
        String name
) { }
