package com.cineworld.cinemetro.application.dto.cinema.hall.response;

public record GetHallResponseDto(
        Long id,
        String name,
        Integer capacity,
        Long buildingId
) { }
