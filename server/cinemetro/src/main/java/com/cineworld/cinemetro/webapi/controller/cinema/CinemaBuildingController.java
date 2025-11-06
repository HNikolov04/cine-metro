package com.cineworld.cinemetro.webapi.controller.cinema;

import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request.CreateCinemaBuildingRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request.UpdateCinemaBuildingRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetAllCinemaBuildingsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetCinemaBuildingResponseDto;
import com.cineworld.cinemetro.application.service.cinema.CinemaBuildingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class CinemaBuildingController {

    private final CinemaBuildingService cinemaBuildingService;

    @GetMapping
    public ResponseEntity<List<GetAllCinemaBuildingsResponseDto>> getAll() {
        return ResponseEntity.ok(cinemaBuildingService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCinemaBuildingResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaBuildingService.getById(id));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<GetAllCinemaBuildingsResponseDto>> getByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(cinemaBuildingService.getByCity(cityId));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetCinemaBuildingResponseDto> create(
            @RequestBody @Valid CreateCinemaBuildingRequestDto request) {
        var response = cinemaBuildingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetCinemaBuildingResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCinemaBuildingRequestDto request) {
        var response = cinemaBuildingService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cinemaBuildingService.delete(id);
    }
}