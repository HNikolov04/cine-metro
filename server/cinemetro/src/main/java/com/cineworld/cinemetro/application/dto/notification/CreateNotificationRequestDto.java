package com.cineworld.cinemetro.application.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationRequestDto(
        @NotNull
        Long userId,
        @NotBlank
        String title,
        @NotBlank
        String message
) {}
