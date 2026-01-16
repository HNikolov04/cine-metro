package com.cineworld.cinemetro.application.service.cinema;

import com.cineworld.cinemetro.application.dto.cinema.seat.request.CreateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.request.UpdateSeatRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetAllSeatsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.seat.response.GetSeatResponseDto;
import com.cineworld.cinemetro.application.mapper.cinema.SeatMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.seat.SeatNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.Hall;
import com.cineworld.cinemetro.domain.model.cinema.Seat;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.domain.model.ticket.Ticket;
import com.cineworld.cinemetro.persistence.repository.cinema.HallRepository;
import com.cineworld.cinemetro.persistence.repository.cinema.SeatRepository;
import com.cineworld.cinemetro.persistence.repository.screening.ScreeningRepository;
import com.cineworld.cinemetro.persistence.repository.ticket.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;
    private final ScreeningRepository screeningRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public List<GetAllSeatsResponseDto> getAll() {
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetSeatResponseDto getById(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException(id));
        return SeatMapper.toGetSeatDto(seat);
    }

    @Transactional
    public List<GetAllSeatsResponseDto> getByHall(Long hallId) {
        return seatRepository.findAllByHall_Id(hallId)
                .stream()
                .map(SeatMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public List<GetAllSeatsResponseDto> getAvailableByScreening(Long screeningId) {
        Screening screening = screeningRepository.findById(screeningId)
                .orElseThrow(() -> new ScreeningNotFoundException(screeningId));

        List<Seat> seats = seatRepository.findAllByHall_Id(screening.getHall().getId());
        List<Ticket> tickets = ticketRepository.findAllByScreening_Id(screeningId);

        List<Long> bookedSeatIds = tickets.stream()
                .map(ticket -> ticket.getSeat().getId())
                .toList();

        return seats.stream()
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .map(SeatMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetSeatResponseDto create(CreateSeatRequestDto req) {
        Hall hall = hallRepository.findById(req.hallId())
                .orElseThrow(() -> new HallNotFoundException(req.hallId()));

        if (seatRepository.existsByHall_IdAndRowNumberAndSeatNumber(
                req.hallId(), req.rowNumber(), req.seatNumber())) {
            throw new SeatAlreadyExistsException(req.hallId(), req.rowNumber(), req.seatNumber());
        }

        Seat seat = SeatMapper.toEntity(req);
        seat.setHall(hall);
        Seat saved = seatRepository.save(seat);
        return SeatMapper.toGetSeatDto(saved);
    }

    @Transactional
    public GetSeatResponseDto update(Long id, UpdateSeatRequestDto req) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException(id));

        boolean hallChanged = !seat.getHall().getId().equals(req.hallId());
        boolean positionChanged = !seat.getRowNumber().equals(req.rowNumber())
                || !seat.getSeatNumber().equals(req.seatNumber());

        if ((hallChanged || positionChanged)
                && seatRepository.existsByHall_IdAndRowNumberAndSeatNumber(
                req.hallId(), req.rowNumber(), req.seatNumber())) {
            throw new SeatAlreadyExistsException(req.hallId(), req.rowNumber(), req.seatNumber());
        }

        if (hallChanged) {
            Hall hall = hallRepository.findById(req.hallId())
                    .orElseThrow(() -> new HallNotFoundException(req.hallId()));
            seat.setHall(hall);
        }

        SeatMapper.applyUpdate(seat, req);
        return SeatMapper.toGetSeatDto(seat);
    }

    @Transactional
    public void delete(Long id) {
        if (!seatRepository.existsById(id)) {
            throw new SeatNotFoundException(id);
        }
        seatRepository.deleteById(id);
    }
}
