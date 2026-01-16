package com.cineworld.cinemetro.application.mapper.movie;

import com.cineworld.cinemetro.application.dto.movie.request.CreateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.request.UpdateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetAllMoviesResponseDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetMovieResponseDto;
import com.cineworld.cinemetro.domain.model.movie.Movie;

public class MovieMapper {

    public static Movie toEntity(CreateMovieRequestDto req) {
        return Movie.builder()
                .title(req.title())
                .durationMinutes(req.durationMinutes())
                .description(req.description())
                .rating(req.rating())
                .build();
    }

    public static void applyUpdate(Movie movie, UpdateMovieRequestDto req) {
        movie.setTitle(req.title());
        movie.setDurationMinutes(req.durationMinutes());
        movie.setDescription(req.description());
        movie.setRating(req.rating());
    }

    public static GetMovieResponseDto toGetMovieDto(Movie movie) {
        return new GetMovieResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getDescription(),
                movie.getRating()
        );
    }

    public static GetAllMoviesResponseDto toGetAllDto(Movie movie) {
        return new GetAllMoviesResponseDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDurationMinutes(),
                movie.getRating()
        );
    }
}
