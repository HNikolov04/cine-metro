package com.cineworld.cinemetro.webapi.controller.cinema;

import com.cineworld.cinemetro.application.dto.cinema.city.request.CreateCityRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.city.request.UpdateCityRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetAllCitiesResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetCityResponseDto;
import com.cineworld.cinemetro.application.service.cinema.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<GetAllCitiesResponseDto>> getAll() {
        List<GetAllCitiesResponseDto> response = cityService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCityResponseDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(cityService.getById(id));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GetCityResponseDto> create(@RequestBody @Valid CreateCityRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.create(request));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GetCityResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateCityRequestDto request) {
        return ResponseEntity.ok(cityService.update(id, request));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }
}