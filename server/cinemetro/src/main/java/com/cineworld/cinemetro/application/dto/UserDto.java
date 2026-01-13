package com.cineworld.cinemetro.application.dto;

import com.cineworld.cinemetro.domain.enums.UserRole;
import lombok.Builder;

@Builder
public record UserDto(
        Long id,
        String email,
        UserRole role
) {}
