package com.cineworld.cinemetro.webapi.controller.cinema;

import com.cineworld.cinemetro.application.dto.cinema.seat.request.CreateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.request.UpdateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetAllSeatsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetSeatResponseDto;
import com.cineworld.cinemetro.application.service.cinema.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<GetAllSeatsResponseDto>> getAll() {
        return ResponseEntity.ok(seatService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSeatResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getById(id));
    }

    @GetMapping("/by-hall/{hallId}")
    public ResponseEntity<List<GetAllSeatsResponseDto>> getByHall(@PathVariable Long hallId) {
        return ResponseEntity.ok(seatService.getByHall(hallId));
    }

    @GetMapping("/by-screening/{screeningId}/available")
    public ResponseEntity<List<GetAllSeatsResponseDto>> getAvailableByScreening(@PathVariable Long screeningId) {
        return ResponseEntity.ok(seatService.getAvailableByScreening(screeningId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetSeatResponseDto> create(@RequestBody @Valid CreateSeatRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetSeatResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid UpdateSeatRequestDto request) {
        return ResponseEntity.ok(seatService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        seatService.delete(id);
    }
}
