package com.cineworld.cinemetro.application.service.screening;

import com.cineworld.cinemetro.application.dto.screening.request.CreateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.request.UpdateScreeningRequestDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetAllScreeningsResponseDto;
import com.cineworld.cinemetro.application.dto.screening.response.GetScreeningResponseDto;
import com.cineworld.cinemetro.application.mapper.screening.ScreeningMapper;
import com.cineworld.cinemetro.domain.exceptions.cinema.hall.HallNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.movie.MovieNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningTimeConflictException;
import com.cineworld.cinemetro.domain.exceptions.screening.ScreeningNotFoundException;
import com.cineworld.cinemetro.domain.model.cinema.Hall;
import com.cineworld.cinemetro.domain.model.movie.Movie;
import com.cineworld.cinemetro.domain.model.screening.Screening;
import com.cineworld.cinemetro.persistence.repository.cinema.HallRepository;
import com.cineworld.cinemetro.persistence.repository.movie.MovieRepository;
import com.cineworld.cinemetro.persistence.repository.screening.ScreeningRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private static final long CLEANUP_MINUTES = 15;

    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    @Transactional
    public List<GetAllScreeningsResponseDto> getAll() {
        return screeningRepository.findAll()
                .stream()
                .map(ScreeningMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetScreeningResponseDto getById(Long id) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException(id));
        return ScreeningMapper.toGetScreeningDto(screening);
    }

    @Transactional
    public List<GetAllScreeningsResponseDto> getByMovie(Long movieId) {
        return screeningRepository.findAllByMovie_Id(movieId)
                .stream()
                .map(ScreeningMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public List<GetAllScreeningsResponseDto> getByHall(Long hallId) {
        return screeningRepository.findAllByHall_Id(hallId)
                .stream()
                .map(ScreeningMapper::toGetAllDto)
                .toList();
    }

    @Transactional
    public GetScreeningResponseDto create(CreateScreeningRequestDto req) {
        Movie movie = movieRepository.findById(req.movieId())
                .orElseThrow(() -> new MovieNotFoundException(req.movieId()));
        Hall hall = hallRepository.findById(req.hallId())
                .orElseThrow(() -> new HallNotFoundException(req.hallId()));

        assertNoOverlap(hall.getId(), req.startTime(), movie.getDurationMinutes(), null);

        Screening screening = ScreeningMapper.toEntity(req);
        screening.setMovie(movie);
        screening.setHall(hall);
        Screening saved = screeningRepository.save(screening);
        return ScreeningMapper.toGetScreeningDto(saved);
    }

    @Transactional
    public GetScreeningResponseDto update(Long id, UpdateScreeningRequestDto req) {
        Screening screening = screeningRepository.findById(id)
                .orElseThrow(() -> new ScreeningNotFoundException(id));

        boolean hallChanged = !screening.getHall().getId().equals(req.hallId());
        boolean timeChanged = !screening.getStartTime().equals(req.startTime());
        boolean movieChanged = !screening.getMovie().getId().equals(req.movieId());

        Movie movie = screening.getMovie();
        if (movieChanged) {
            movie = movieRepository.findById(req.movieId())
                    .orElseThrow(() -> new MovieNotFoundException(req.movieId()));
        }

        if (hallChanged || timeChanged || movieChanged) {
            assertNoOverlap(req.hallId(), req.startTime(), movie.getDurationMinutes(), screening.getId());
        }

        if (hallChanged) {
            Hall hall = hallRepository.findById(req.hallId())
                    .orElseThrow(() -> new HallNotFoundException(req.hallId()));
            screening.setHall(hall);
        }

        if (movieChanged) {
            screening.setMovie(movie);
        }

        ScreeningMapper.applyUpdate(screening, req);
        return ScreeningMapper.toGetScreeningDto(screening);
    }

    @Transactional
    public void delete(Long id) {
        if (!screeningRepository.existsById(id)) {
            throw new ScreeningNotFoundException(id);
        }
        screeningRepository.deleteById(id);
    }

    private void assertNoOverlap(Long hallId, LocalDateTime startTime, Integer durationMinutes, Long excludeId) {
        int duration = durationMinutes != null ? durationMinutes : 0;
        LocalDateTime newStart = startTime;
        LocalDateTime newEnd = startTime.plusMinutes(duration + CLEANUP_MINUTES);

        List<Screening> screenings = screeningRepository.findAllByHall_Id(hallId);
        for (Screening existing : screenings) {
            if (excludeId != null && existing.getId().equals(excludeId)) {
                continue;
            }
            int existingDuration = existing.getMovie() != null && existing.getMovie().getDurationMinutes() != null
                    ? existing.getMovie().getDurationMinutes()
                    : 0;
            LocalDateTime existingStart = existing.getStartTime();
            LocalDateTime existingEnd = existingStart.plusMinutes(existingDuration + CLEANUP_MINUTES);

            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                throw new ScreeningTimeConflictException(hallId, startTime);
            }
        }
    }
}
