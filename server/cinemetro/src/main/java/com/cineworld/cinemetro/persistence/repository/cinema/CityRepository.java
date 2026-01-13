package com.cineworld.cinemetro.persistence.repository.cinema;

import com.cineworld.cinemetro.domain.model.cinema.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByNameIgnoreCase(String name);
}
