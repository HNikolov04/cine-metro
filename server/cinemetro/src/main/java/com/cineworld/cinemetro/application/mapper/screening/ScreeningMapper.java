package com.cineworld.cinemetro.application.mapper.screening;

import com.cineworld.cinemetro.application.dto.screening.request.CreateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.request.UpdateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetAllScreeningsResponseDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetScreeningResponseDto;
import com.cineworld.cinemetro.domain.model.screening.Screening;

public class ScreeningMapper {

    public static Screening toEntity(CreateScreeningRequestDto req) {
        return Screening.builder()
                .startTime(req.startTime())
                .screeningType(req.screeningType())
                .basePrice(req.basePrice())
                .build();
    }

    public static void applyUpdate(Screening screening, UpdateScreeningRequestDto req) {
        screening.setStartTime(req.startTime());
        screening.setScreeningType(req.screeningType());
        screening.setBasePrice(req.basePrice());
    }

    public static GetScreeningResponseDto toGetScreeningDto(Screening screening) {
        return new GetScreeningResponseDto(
                screening.getId(),
                screening.getMovie() != null ? screening.getMovie().getId() : null,
                screening.getHall() != null ? screening.getHall().getId() : null,
                screening.getStartTime(),
                screening.getScreeningType(),
                screening.getBasePrice()
        );
    }

    public static GetAllScreeningsResponseDto toGetAllDto(Screening screening) {
        return new GetAllScreeningsResponseDto(
                screening.getId(),
                screening.getMovie() != null ? screening.getMovie().getId() : null,
                screening.getHall() != null ? screening.getHall().getId() : null,
                screening.getStartTime(),
                screening.getScreeningType(),
                screening.getBasePrice()
        );
    }
}
