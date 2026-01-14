package com.cineworld.cinemetro.application.dto.user;

import com.cineworld.cinemetro.domain.enums.user.UserRole;

public record UserDto(
        Long id,
        String email,
        UserRole role
) {}
