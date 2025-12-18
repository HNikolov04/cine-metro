package com.cineworld.cinemetro.application.auth;

import com.cineworld.cinemetro.application.mapper.UserMapper;
import com.cineworld.cinemetro.domain.enums.UserRole;
import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.infrastructure.security.JwtTokenProvider;
import com.cineworld.cinemetro.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;
    //private final UserMapper userMapper;

    public String login(String username, String password){
        String normalizeEmail = username.trim().toLowerCase();
        var user = userRepository.findByEmail(normalizeEmail).orElseThrow(() -> new RuntimeException("User not found!"));
        if(!encoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return jwt.generateToken(username);
    }
    public void register(String email, String password) {
        User user = User.builder()
                .email(email.trim().toLowerCase())
                .password(encoder.encode(password))
                .role(UserRole.CUSTOMER) // default role
                .build();

        userRepository.save(user);
    }
}