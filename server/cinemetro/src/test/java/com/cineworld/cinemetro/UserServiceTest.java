package com.cineworld.cinemetro;

import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.dto.UserDto;
import com.cineworld.cinemetro.application.mapper.UserMapper;
import com.cineworld.cinemetro.application.service.UserService;
import com.cineworld.cinemetro.domain.enums.UserRole;
import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private RegisterUserRequestDto requestDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        requestDto = new RegisterUserRequestDto("test@test.com", "password");
        user = new User();
        user.setEmail("test@test.com");
        userDto = new UserDto( 1L,"test@test.com", UserRole.CUSTOMER);
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(userMapper.registerRequestToUser(requestDto)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(requestDto);

        assertEquals("test@test.com", result.email());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_emailAlreadyExists_throws() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.createUser(requestDto));

        assertEquals("Email already exists!", ex.getMessage());
    }

    @Test
    void findByEmail_success() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.findByEmail("test@test.com");

        assertEquals("test@test.com", result.email());
    }

    @Test
    void getAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("test@test.com", result.getFirst().email());
    }
}
