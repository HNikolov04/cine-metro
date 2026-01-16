package com.cineworld.cinemetro.application.service.movie;

import com.cineworld.cinemetro.application.dto.movie.request.CreateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.request.UpdateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetAllMoviesResponseDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetMovieResponseDto;
import com.cineworld.cinemetro.application.mapper.movie.MovieMapper;
import com.cineworld.cinemetro.domain.exceptions.movie.MovieAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.movie.MovieNotFoundException;
import com.cineworld.cinemetro.domain.model.movie.Movie;
import com.cineworld.cinemetro.persistence.repository.movie.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    @Transactional
    public List<GetAllMoviesResponseDto> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetMovieResponseDto getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        return MovieMapper.toGetMovieDto(movie);
    }

    @Transactional
    public GetMovieResponseDto create(CreateMovieRequestDto req) {
        String title = req.title().trim();
        if (movieRepository.existsByTitleIgnoreCase(title)) {
            throw new MovieAlreadyExistsException(title);
        }

        Movie movie = MovieMapper.toEntity(req);
        movie.setTitle(title);
        Movie saved = movieRepository.save(movie);
        return MovieMapper.toGetMovieDto(saved);
    }

    @Transactional
    public GetMovieResponseDto update(Long id, UpdateMovieRequestDto req) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        String title = req.title().trim();
        if (!movie.getTitle().equalsIgnoreCase(title)
                && movieRepository.existsByTitleIgnoreCase(title)) {
            throw new MovieAlreadyExistsException(title);
        }

        MovieMapper.applyUpdate(movie, req);
        movie.setTitle(title);
        return MovieMapper.toGetMovieDto(movie);
    }

    @Transactional
    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException(id);
        }
        movieRepository.deleteById(id);
    }
}
