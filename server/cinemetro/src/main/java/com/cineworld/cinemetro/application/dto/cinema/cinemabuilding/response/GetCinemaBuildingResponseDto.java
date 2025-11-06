package com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response;

public record GetCinemaBuildingResponseDto(
        Long id,
        String name,
        String address,
        Long cityId
) { }
