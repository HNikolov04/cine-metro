package com.cineworld.cinemetro.application.mapper.cinema;

import com.cineworld.cinemetro.application.dto.cinema.hall.request.CreateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.request.UpdateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetAllHallsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetHallResponseDto;
import com.cineworld.cinemetro.domain.model.cinema.Hall;

public class HallMapper {

    public static Hall toEntity(CreateHallRequestDto req) {
        return Hall.builder()
                .name(req.name())
                .capacity(req.capacity())
                .build();
    }

    public static void applyUpdate(Hall hall, UpdateHallRequestDto req) {
        hall.setName(req.name());
        hall.setCapacity(req.capacity());
    }

    public static GetHallResponseDto toGetHallDto(Hall hall) {
        return new GetHallResponseDto(
                hall.getId(),
                hall.getName(),
                hall.getCapacity(),
                hall.getBuilding() != null ? hall.getBuilding().getId() : null
        );
    }

    public static GetAllHallsResponseDto toGetAllDto(Hall hall) {
        return new GetAllHallsResponseDto(
                hall.getId(),
                hall.getName(),
                hall.getCapacity(),
                hall.getBuilding() != null ? hall.getBuilding().getId() : null
        );
    }
}
