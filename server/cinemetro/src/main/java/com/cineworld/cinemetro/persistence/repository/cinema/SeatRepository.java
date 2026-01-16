package com.cineworld.cinemetro.persistence.repository.cinema;

import com.cineworld.cinemetro.domain.model.cinema.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    boolean existsByHall_IdAndRowNumberAndSeatNumber(Long hallId, Integer rowNumber, Integer seatNumber);

    List<Seat> findAllByHall_Id(Long hallId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.id = :id")
    Optional<Seat> findByIdForUpdate(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Seat s where s.id in :ids")
    List<Seat> findAllByIdForUpdate(@Param("ids") List<Long> ids);
}
