package com.cineworld.cinemetro.application.dto.movie.response;

public record GetMovieResponseDto(
        Long id,
        String title,
        Integer durationMinutes,
        String description,
        String rating
) { }
