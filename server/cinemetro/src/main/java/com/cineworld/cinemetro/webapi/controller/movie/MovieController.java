package com.cineworld.cinemetro.webapi.controller.movie;

import com.cineworld.cinemetro.application.dto.movie.request.CreateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.request.UpdateMovieRequestDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetAllMoviesResponseDto;
import com.cineworld.cinemetro.application.dto.movie.response.GetMovieResponseDto;
import com.cineworld.cinemetro.application.service.movie.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<GetAllMoviesResponseDto>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetMovieResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetMovieResponseDto> create(@RequestBody @Valid CreateMovieRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetMovieResponseDto> update(@PathVariable Long id,
                                                      @RequestBody @Valid UpdateMovieRequestDto request) {
        return ResponseEntity.ok(movieService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }
}
