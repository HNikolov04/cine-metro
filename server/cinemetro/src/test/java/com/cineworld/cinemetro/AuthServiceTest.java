package com.cineworld.cinemetro;

import com.cineworld.cinemetro.application.service.auth.AuthService;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.infrastructure.security.JwtTokenProvider;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtTokenProvider jwt;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser(String email, String password, UserRole role) {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    @Test
    void login_success() {
        String email = "test@example.com";
        String password = "password";

        User user = createUser(email, "encodedPassword", UserRole.CUSTOMER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(encoder.matches(password, "encodedPassword")).thenReturn(true);
        when(jwt.generateToken(email)).thenReturn("jwt-token");

        String token = authService.login(email, password);

        assertEquals("jwt-token", token);

        verify(userRepository).findByEmail(email);
        verify(encoder).matches(password, "encodedPassword");
        verify(jwt).generateToken(email);
    }

    @Test
    void login_userNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(email, "password"));

        assertEquals("User not found!", exception.getMessage());
    }

    @Test
    void login_invalidPassword() {
        String email = "test@example.com";
        String password = "wrongPassword";

        User user = createUser(email, "encodedPassword", UserRole.CUSTOMER);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(encoder.matches(password, "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(email, password));

        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void register_success() {
        String email = "new@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";

        when(encoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        authService.register(email, password);

        verify(userRepository).save(any(User.class));
    }
}
