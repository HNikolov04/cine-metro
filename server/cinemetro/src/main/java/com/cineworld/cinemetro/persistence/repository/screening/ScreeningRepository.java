package com.cineworld.cinemetro.persistence.repository.screening;

import com.cineworld.cinemetro.domain.model.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    boolean existsByHall_IdAndStartTime(Long hallId, LocalDateTime startTime);

    List<Screening> findAllByMovie_Id(Long movieId);

    List<Screening> findAllByHall_Id(Long hallId);
}
