package com.cineworld.cinemetro.application.service.cinema;

import com.cineworld.cinemetro.application.dto.cinema.city.request.CreateCityRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.city.request.UpdateCityRequestDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetAllCitiesResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetCityResponseDto;
import com.cineworld.cinemetro.application.mapper.cinema.CityMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityHasBuildingsException;
import com.cineworld.cinemetro.domain.exceptions.cinema.city.CityNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.City;
import com.cineworld.cinemetro.persistence.repository.cinema.CinemaBuildingRepository;
import com.cineworld.cinemetro.persistence.repository.cinema.CityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final CinemaBuildingRepository cinemaBuildingRepository;

    @Transactional
    public List<GetAllCitiesResponseDto> getAll() {
        return cityRepository.findAll()
                .stream()
                .map(CityMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetCityResponseDto getById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
        return CityMapper.toGetCityDto(city);
    }

    @Transactional
    public GetCityResponseDto create(CreateCityRequestDto req) {
        String name = req.name().trim();
        if (cityRepository.existsByNameIgnoreCase(name)) {
            throw new CityAlreadyExistsException(name);
        }

        City saved = cityRepository.save(City.builder().name(name).build());
        return CityMapper.toGetCityDto(saved);
    }

    @Transactional
    public GetCityResponseDto update(Long id, UpdateCityRequestDto req) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));

        String name = req.name().trim();
        if (!city.getName().equalsIgnoreCase(name)
                && cityRepository.existsByNameIgnoreCase(name)) {
            throw new CityAlreadyExistsException(name);
        }

        city.setName(name);
        return CityMapper.toGetCityDto(city);
    }

    @Transactional
    public void delete(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
        if (cinemaBuildingRepository.existsByCity_Id(id)) {
            throw new CityHasBuildingsException(id);
        }
        cityRepository.delete(city);
    }
}
