package com.cineworld.cinemetro.application.dto.user;

import com.cineworld.cinemetro.domain.enums.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto(
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 6, max = 100)
        String password,
        UserRole role
) {}
