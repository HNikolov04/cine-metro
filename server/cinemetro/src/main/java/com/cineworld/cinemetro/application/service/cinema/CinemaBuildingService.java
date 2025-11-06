package com.cineworld.cinemetro.application.service.cinema;

import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request.CreateCinemaBuildingRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.request.UpdateCinemaBuildingRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetAllCinemaBuildingsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetCinemaBuildingResponseDto;
import com.cineworld.cinemetro.application.mapper.cinema.CinemaBuildingMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.cinemabuilding.CinemaBuildingNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.CinemaBuilding;
import com.cineworld.cinemetro.domain.model.cinema.City;
import com.cineworld.cinemetro.persistence.repository.cinema.CinemaBuildingRepository;
import com.cineworld.cinemetro.persistence.repository.cinema.CityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaBuildingService {

    private final CinemaBuildingRepository cinemaBuildingRepository;
    private final CityRepository cityRepository;

    @Transactional
    public GetCinemaBuildingResponseDto create(CreateCinemaBuildingRequestDto req) {
        City city = cityRepository.findById(req.cityId())
                .orElseThrow(() -> new CityNotFoundException(req.cityId()));

        if (cinemaBuildingRepository.existsByCity_IdAndNameIgnoreCase(req.cityId(), req.name())) {
            throw new CinemaBuildingAlreadyExistsException(req.cityId(), req.name());
        }

        CinemaBuilding saved = cinemaBuildingRepository.save(
                CinemaBuilding.builder()
                        .name(req.name())
                        .address(req.address())
                        .city(city)
                        .build()
        );

        return CinemaBuildingMapper.toGetCinemaBuildingDto(saved);
    }

    @Transactional
    public GetCinemaBuildingResponseDto update(Long id, UpdateCinemaBuildingRequestDto req) {
        CinemaBuilding building = cinemaBuildingRepository.findById(id)
                .orElseThrow(() -> new CinemaBuildingNotFoundException(id));

        if (!building.getName().equalsIgnoreCase(req.name())
                && cinemaBuildingRepository.existsByCity_IdAndNameIgnoreCase(req.cityId(), req.name())) {
            throw new CinemaBuildingAlreadyExistsException(req.cityId(), req.name());
        }

        if (!building.getCity().getId().equals(req.cityId())) {
            City newCity = cityRepository.findById(req.cityId())
                    .orElseThrow(() -> new CityNotFoundException(req.cityId()));
            building.setCity(newCity);
        }

        building.setName(req.name());
        building.setAddress(req.address());
        return CinemaBuildingMapper.toGetCinemaBuildingDto(building);
    }

    public GetCinemaBuildingResponseDto getById(Long id) {
        CinemaBuilding b = cinemaBuildingRepository.findById(id)
                .orElseThrow(() -> new CinemaBuildingNotFoundException(id));
        return CinemaBuildingMapper.toGetCinemaBuildingDto(b);
    }

    public List<GetAllCinemaBuildingsResponseDto> getAll() {
        return cinemaBuildingRepository.findAll()
                .stream()
                .map(CinemaBuildingMapper::toGetAllDto)
                .toList();
    }

    public List<GetAllCinemaBuildingsResponseDto> getByCity(Long cityId) {
        return cinemaBuildingRepository.findAllByCity_Id(cityId)
                .stream()
                .map(CinemaBuildingMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        if (!cinemaBuildingRepository.existsById(id)) {
            throw new CinemaBuildingNotFoundException(id);
        }
        cinemaBuildingRepository.deleteById(id);
    }
}