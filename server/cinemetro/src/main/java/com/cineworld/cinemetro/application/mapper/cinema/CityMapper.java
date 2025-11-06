package com.cineworld.cinemetro.application.mapper.cinema;

import com.cineworld.cinemetro.application.dto.cinema.cinemabuilding.response.GetAllCinemaBuildingsResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetAllCitiesResponseDto;
import com.cineworld.cinemetro.application.dto.cinema.city.response.GetCityResponseDto;
import com.cineworld.cinemetro.domain.model.cinema.City;

import java.util.List;

public class CityMapper {

    public static GetCityResponseDto toGetCityDto(City city) {
        return new GetCityResponseDto(city.getId(), city.getName());
    }

    public static GetAllCitiesResponseDto toGetAllDto(City city) {
        List<GetAllCinemaBuildingsResponseDto> buildings = city.getBuildings()
                .stream()
                .map(CinemaBuildingMapper::toGetAllDto)
                .toList();

        return new GetAllCitiesResponseDto(city.getId(), city.getName(), buildings);
    }
}