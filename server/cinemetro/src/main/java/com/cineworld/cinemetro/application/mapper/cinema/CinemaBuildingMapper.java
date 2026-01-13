package com.cineworld.cinemetro.application.mapper.cinema;

import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetAllCinemaBuildingsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetCinemaBuildingResponseDto;
import com.cineworld.cinemetro.domain.model.cinema.CinemaBuilding;

public class CinemaBuildingMapper {

    public static GetCinemaBuildingResponseDto toGetCinemaBuildingDto(CinemaBuilding b) {
        return new GetCinemaBuildingResponseDto(
                b.getId(),
                b.getName(),
                b.getAddress(),
                b.getCity() != null ? b.getCity().getId() : null
        );
    }

    public static GetAllCinemaBuildingsResponseDto toGetAllDto(CinemaBuilding b) {
        return new GetAllCinemaBuildingsResponseDto(
                b.getId(),
                b.getName(),
                b.getAddress(),
                b.getCity() != null ? b.getCity().getId() : null
        );
    }
}


