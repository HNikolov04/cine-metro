package com.cineworld.cinemetro.application.service.user;

import com.cineworld.cinemetro.application.dto.user.CreateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UpdateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UserDto;
import com.cineworld.cinemetro.application.mapper.user.UserMapper;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.exceptions.user.UserAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toDto(user);
    }

    public List<UserDto> getByRole(UserRole userRole) {
        return userRepository.findByRole(userRole)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto createUser(CreateUserRequestDto request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException(normalizedEmail);
        }
        User user = userMapper.fromCreateRequest(request);
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role() != null ? request.role() : UserRole.CUSTOMER);
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        String normalizedEmail = request.email().trim().toLowerCase();
        if (!user.getEmail().equalsIgnoreCase(normalizedEmail)
                && userRepository.existsByEmail(normalizedEmail)) {
            throw new UserAlreadyExistsException(normalizedEmail);
        }

        userMapper.applyUpdate(user, request);
        user.setEmail(normalizedEmail);
        return userMapper.toDto(userRepository.save(user));
    }

}
