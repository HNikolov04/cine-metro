package com.cineworld.cinemetro.persistence.repository.cinema;

import com.cineworld.cinemetro.domain.model.cinema.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    boolean existsByBuilding_IdAndNameIgnoreCase(Long buildingId, String name);

    List<Hall> findAllByBuilding_Id(Long buildingId);
}
