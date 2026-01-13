package com.cineworld.cinemetro.application.dto.cinema.city.response;

import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetAllCinemaBuildingsResponseDto;

import java.util.List;

public record GetAllCitiesResponseDto(
        Long id,
        String name,
        List<GetAllCinemaBuildingsResponseDto> buildings
) { }
