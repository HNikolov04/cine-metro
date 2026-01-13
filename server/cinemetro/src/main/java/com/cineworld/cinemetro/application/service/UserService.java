package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.dto.UserDto;
import com.cineworld.cinemetro.application.mapper.UserMapper;
import com.cineworld.cinemetro.domain.enums.UserRole;

import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
        return userMapper.toDto(user);
    }

    public List<UserDto> getByRole(UserRole userRole) {
        return userRepository.findByRole(userRole)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto createUser(RegisterUserRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists!");
        }
        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id:" + id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUserById(Long id) { //TO DO
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        return userMapper.toDto(user);
    }

}