package com.cineworld.cinemetro.unit;

import com.cineworld.cinemetro.application.dto.user.CreateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UpdateUserRequestDto;
import com.cineworld.cinemetro.application.dto.user.UserDto;
import com.cineworld.cinemetro.application.mapper.user.UserMapper;
import com.cineworld.cinemetro.application.service.user.UserService;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.exceptions.user.UserAlreadyExistsException;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private CreateUserRequestDto createRequest;
    private UpdateUserRequestDto updateRequest;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        createRequest = new CreateUserRequestDto("test@test.com", "password", UserRole.CUSTOMER);
        updateRequest = new UpdateUserRequestDto("updated@test.com", UserRole.ADMIN);
        user = new User();
        user.setEmail("test@test.com");
        userDto = new UserDto(1L, "test@test.com", UserRole.CUSTOMER);
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(userMapper.fromCreateRequest(createRequest)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(createRequest);

        assertEquals("test@test.com", result.email());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_emailAlreadyExists_throws() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(createRequest));

        assertEquals("User already exists with email: test@test.com", ex.getMessage());
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

    @Test
    void updateUser_notFound_throws() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(1L, updateRequest));

        assertEquals("User not found with id: 1", ex.getMessage());
    }
}
