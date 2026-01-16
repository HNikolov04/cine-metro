package com.cineworld.cinemetro.application.service.cinema;

import com.cineworld.cinemetro.application.dto.cinema.hall.request.CreateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.request.UpdateHallRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetAllHallsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.hall.response.GetHallResponseDto;
import com.cineworld.cinemetro.application.mapper.cinema.HallMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.CinemaBuilding;
import com.cineworld.cinemetro.domain.model.cinema.Hall;
import com.cineworld.cinemetro.persistence.repository.cinema.CinemaBuildingRepository;
import com.cineworld.cinemetro.persistence.repository.cinema.HallRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {

    private final HallRepository hallRepository;
    private final CinemaBuildingRepository cinemaBuildingRepository;

    @Transactional
    public List<GetAllHallsResponseDto> getAll() {
        return hallRepository.findAll()
                .stream()
                .map(HallMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetHallResponseDto getById(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException(id));
        return HallMapper.toGetHallDto(hall);
    }

    @Transactional
    public List<GetAllHallsResponseDto> getByBuilding(Long buildingId) {
        return hallRepository.findAllByBuilding_Id(buildingId)
                .stream()
                .map(HallMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetHallResponseDto create(CreateHallRequestDto req) {
        CinemaBuilding building = cinemaBuildingRepository.findById(req.buildingId())
                .orElseThrow(() -> new CinemaBuildingNotFoundException(req.buildingId()));

        String name = req.name().trim();
        if (hallRepository.existsByBuilding_IdAndNameIgnoreCase(req.buildingId(), name)) {
            throw new HallAlreadyExistsException(req.buildingId(), name);
        }

        Hall hall = HallMapper.toEntity(req);
        hall.setName(name);
        hall.setBuilding(building);
        Hall saved = hallRepository.save(hall);
        return HallMapper.toGetHallDto(saved);
    }

    @Transactional
    public GetHallResponseDto update(Long id, UpdateHallRequestDto req) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new HallNotFoundException(id));

        String name = req.name().trim();
        boolean nameChanged = !hall.getName().equalsIgnoreCase(name);
        boolean buildingChanged = !hall.getBuilding().getId().equals(req.buildingId());

        if ((nameChanged || buildingChanged)
                && hallRepository.existsByBuilding_IdAndNameIgnoreCase(req.buildingId(), name)) {
            throw new HallAlreadyExistsException(req.buildingId(), name);
        }

        if (buildingChanged) {
            CinemaBuilding newBuilding = cinemaBuildingRepository.findById(req.buildingId())
                    .orElseThrow(() -> new CinemaBuildingNotFoundException(req.buildingId()));
            hall.setBuilding(newBuilding);
        }

        HallMapper.applyUpdate(hall, req);
        hall.setName(name);
        return HallMapper.toGetHallDto(hall);
    }

    @Transactional
    public void delete(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new HallNotFoundException(id);
        }
        hallRepository.deleteById(id);
    }
}
