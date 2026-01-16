package com.cineworld.cinemetro.webapi.controller.cinema;

import com.cineworld.cinemetro.application.dto.cinema.hall.request.CreateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.request.UpdateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetAllHallsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetHallResponseDto;
import com.cineworld.cinemetro.application.service.cinema.HallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;

    @GetMapping
    public ResponseEntity<List<GetAllHallsResponseDto>> getAll() {
        return ResponseEntity.ok(hallService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetHallResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.getById(id));
    }

    @GetMapping("/by-building/{buildingId}")
    public ResponseEntity<List<GetAllHallsResponseDto>> getByBuilding(@PathVariable Long buildingId) {
        return ResponseEntity.ok(hallService.getByBuilding(buildingId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetHallResponseDto> create(@RequestBody @Valid CreateHallRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hallService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetHallResponseDto> update(@PathVariable Long id,
                                                     @RequestBody @Valid UpdateHallRequestDto request) {
        return ResponseEntity.ok(hallService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        hallService.delete(id);
    }
}
