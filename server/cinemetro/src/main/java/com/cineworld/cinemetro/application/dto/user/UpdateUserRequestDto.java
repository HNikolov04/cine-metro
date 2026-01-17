package com.cineworld.cinemetro.application.dto.user;

import com.cineworld.cinemetro.domain.enums.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDto(
        @NotBlank
        @Email
        String email,
        @NotNull
        UserRole role
) {}
