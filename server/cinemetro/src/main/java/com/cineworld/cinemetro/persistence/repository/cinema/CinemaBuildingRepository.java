package com.cineworld.cinemetro.persistence.repository.cinema;

import com.cineworld.cinemetro.domain.model.cinema.CinemaBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaBuildingRepository extends JpaRepository<CinemaBuilding, Long> {
    boolean existsByCity_IdAndNameIgnoreCase(Long cityId, String name);

    List<CinemaBuilding> findAllByCity_Id(Long cityId);
}