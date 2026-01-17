package com.cineworld.cinemetro;

import com.cineworld.cinemetro.application.service.auth.AuthService;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.exceptions.user.InvalidCredentialsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class AuthServiceITest {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceITest(AuthService authService,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void registerAndLoginSuccess() {
        String email = "service@test.com";
        String password = "mypassword";

        String registerToken = authService.register(email, password);
        assertNotNull(registerToken);

        User savedUser = userRepository.findByEmail(email).orElseThrow();
        assertEquals(email, savedUser.getEmail());
        assertEquals(UserRole.CUSTOMER, savedUser.getRole());
        assertTrue(passwordEncoder.matches(password, savedUser.getPassword()));

        String token = authService.login(email, password);
        assertNotNull(token);
    }

    @Test
    void loginWithWrongPasswordShouldFail() {
        String email = "service@test.com";
        String password = "mypassword";

        authService.register(email, password);

        InvalidCredentialsException ex = assertThrows(InvalidCredentialsException.class,
                () -> authService.login(email, "wrongpassword"));
        assertEquals("Invalid credentials.", ex.getMessage());
    }

    @Test
    void loginNonExistentUserShouldFail() {
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> authService.login("notfound@test.com", "password"));
        assertEquals("User not found with email: notfound@test.com", ex.getMessage());
    }
}
