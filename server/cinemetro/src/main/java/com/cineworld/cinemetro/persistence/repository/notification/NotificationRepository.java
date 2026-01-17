package com.cineworld.cinemetro.persistence.repository.notification;

import com.cineworld.cinemetro.domain.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

    Optional<Notification> findByIdAndUser_Id(Long id, Long userId);
}
