package com.cineworld.cinemetro.webapi.controller.screening;

import com.cineworld.cinemetro.application.dto.screening.request.CreateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.request.UpdateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetAllScreeningsResponseDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetScreeningResponseDto;
import com.cineworld.cinemetro.application.service.screening.ScreeningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screenings")
@RequiredArgsConstructor
public class ScreeningController {

    private final ScreeningService screeningService;

    @GetMapping
    public ResponseEntity<List<GetAllScreeningsResponseDto>> getAll() {
        return ResponseEntity.ok(screeningService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetScreeningResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(screeningService.getById(id));
    }

    @GetMapping("/by-movie/{movieId}")
    public ResponseEntity<List<GetAllScreeningsResponseDto>> getByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(screeningService.getByMovie(movieId));
    }

    @GetMapping("/by-hall/{hallId}")
    public ResponseEntity<List<GetAllScreeningsResponseDto>> getByHall(@PathVariable Long hallId) {
        return ResponseEntity.ok(screeningService.getByHall(hallId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetScreeningResponseDto> create(@RequestBody @Valid CreateScreeningRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(screeningService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetScreeningResponseDto> update(@PathVariable Long id,
                                                          @RequestBody @Valid UpdateScreeningRequestDto request) {
        return ResponseEntity.ok(screeningService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        screeningService.delete(id);
    }
}
