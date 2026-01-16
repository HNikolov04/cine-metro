package com.cineworld.cinemetro.application.dto.movie.response;

public record GetAllMoviesResponseDto(
        Long id,
        String title,
        Integer durationMinutes,
        String rating
) { }
