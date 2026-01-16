package com.cineworld.cinemetro.application.dto.movie.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateMovieRequestDto(
        @NotBlank
        @Size(max = 200)
        String title,
        @NotNull
        @Positive
        Integer durationMinutes,
        @Size(max = 1000)
        String description,
        @Size(max = 20)
        String rating
) { }
