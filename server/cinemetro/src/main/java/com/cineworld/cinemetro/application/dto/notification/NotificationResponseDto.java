package com.cineworld.cinemetro.application.dto.notification;

import java.time.LocalDateTime;

public record NotificationResponseDto(
        Long id,
        Long userId,
        String title,
        String message,
        boolean read,
        LocalDateTime createdAt
) {}
