package com.cineworld.cinemetro.application.mapper.notification;

import com.cineworld.cinemetro.application.dto.notification.CreateNotificationRequestDto;
import com.cineworld.cinemetro.application.dto.notification.NotificationResponseDto;
import com.cineworld.cinemetro.domain.model.notification.Notification;
import com.cineworld.cinemetro.domain.model.user.User;

public class NotificationMapper {

    public static Notification toEntity(CreateNotificationRequestDto request, User user) {
        return Notification.builder()
                .user(user)
                .title(request.title())
                .message(request.message())
                .read(false)
                .build();
    }

    public static NotificationResponseDto toDto(Notification notification) {
        return new NotificationResponseDto(
                notification.getId(),
                notification.getUser() != null ? notification.getUser().getId() : null,
                notification.getTitle(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
