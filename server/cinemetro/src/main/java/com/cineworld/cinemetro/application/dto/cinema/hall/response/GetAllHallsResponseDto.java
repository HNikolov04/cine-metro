package com.cineworld.cinemetro.application.dto.cinema.hall.response;

public record GetAllHallsResponseDto(
        Long id,
        String name,
        Integer capacity,
        Long buildingId
) { }
