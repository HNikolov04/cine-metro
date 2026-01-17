package com.cineworld.cinemetro.application.service.auth;

import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.exceptions.user.InvalidCredentialsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.infrastructure.security.JwtTokenProvider;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;

    public String login(String username, String password){
        String normalizedEmail = username.trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException(normalizedEmail));
        if(!encoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        return jwt.generateToken(normalizedEmail);
    }

    public String register(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException(normalizedEmail);
        }
        User user = User.builder()
                .email(normalizedEmail)
                .password(encoder.encode(password))
                .role(UserRole.CUSTOMER)
                .build();

        userRepository.save(user);
        return jwt.generateToken(normalizedEmail);
    }

    public void changePassword(String email, String currentPassword, String newPassword) {
        String normalizedEmail = email.trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UserNotFoundException(normalizedEmail));
        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Current password is incorrect.");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}
