package com.cineworld.cinemetro;

import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import com.cineworld.cinemetro.application.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

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

        // Register
        authService.register(email, password);

        User savedUser = userRepository.findByEmail(email).orElseThrow();
        assertEquals(email, savedUser.getEmail());
        assertEquals(UserRole.CUSTOMER, savedUser.getRole());
        assertTrue(passwordEncoder.matches(password, savedUser.getPassword()));

        // Login
        String token = authService.login(email, password);
        assertNotNull(token);
    }

    @Test
    void loginWithWrongPasswordShouldFail() {
        String email = "service@test.com";
        String password = "mypassword";

        authService.register(email, password);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(email, "wrongpassword"));
        assertEquals("Invalid password", ex.getMessage());
    }

    @Test
    void loginNonExistentUserShouldFail() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login("notfound@test.com", "password"));
        assertEquals("User not found!", ex.getMessage());
    }
}
