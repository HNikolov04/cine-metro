package com.cineworld.cinemetro.application.dto;

import com.cineworld.cinemetro.domain.enums.UserRole;

public record UserDto(
        Long id,
        String email,
        UserRole role
) {}
