package com.cineworld.cinemetro.persistence.repository.movie;

import com.cineworld.cinemetro.domain.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitleIgnoreCase(String title);
}
