package com.cineworld.cinemetro.application.mapper.cinema;

import com.cineworld.cinemetro.application.dto.cinema.seat.request.CreateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.request.UpdateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetAllSeatsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetSeatResponseDto;
import com.cineworld.cinemetro.domain.model.cinema.Seat;

public class SeatMapper {

    public static Seat toEntity(CreateSeatRequestDto req) {
        return Seat.builder()
                .rowNumber(req.rowNumber())
                .seatNumber(req.seatNumber())
                .seatType(req.seatType())
                .build();
    }

    public static void applyUpdate(Seat seat, UpdateSeatRequestDto req) {
        seat.setRowNumber(req.rowNumber());
        seat.setSeatNumber(req.seatNumber());
        seat.setSeatType(req.seatType());
    }

    public static GetSeatResponseDto toGetSeatDto(Seat seat) {
        return new GetSeatResponseDto(
                seat.getId(),
                seat.getRowNumber(),
                seat.getSeatNumber(),
                seat.getSeatType(),
                seat.getHall() != null ? seat.getHall().getId() : null
        );
    }

    public static GetAllSeatsResponseDto toGetAllDto(Seat seat) {
        return new GetAllSeatsResponseDto(
                seat.getId(),
                seat.getRowNumber(),
                seat.getSeatNumber(),
                seat.getSeatType(),
                seat.getHall() != null ? seat.getHall().getId() : null
        );
    }
}
