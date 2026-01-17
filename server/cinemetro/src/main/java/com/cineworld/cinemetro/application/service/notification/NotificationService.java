package com.cineworld.cinemetro.application.service.notification;

import com.cineworld.cinemetro.application.dto.notification.CreateNotificationRequestDto;
import com.cineworld.cinemetro.application.dto.notification.NotificationResponseDto;
import com.cineworld.cinemetro.application.mapper.notification.NotificationMapper;
import com.cineworld.cinemetro.domain.exceptions.notification.NotificationNotFoundException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.notification.Notification;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.notification.NotificationRepository;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public NotificationResponseDto create(CreateNotificationRequestDto request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Notification notification = NotificationMapper.toEntity(request, user);
        return NotificationMapper.toDto(notificationRepository.save(notification));
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return notificationRepository.findAllByUser_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getForEmail(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UserNotFoundException(email));
        return notificationRepository.findAllByUser_IdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(NotificationMapper::toDto)
                .toList();
    }

    @Transactional
    public NotificationResponseDto markRead(Long id, String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UserNotFoundException(email));
        Notification notification = notificationRepository.findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new NotificationNotFoundException(id));
        if (!notification.isRead()) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
        return NotificationMapper.toDto(notification);
    }
}
