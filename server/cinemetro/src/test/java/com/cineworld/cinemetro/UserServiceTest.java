/*package com.cineworld.cinemetro;

import com.cineworld.cinemetro.application.dto.RegisterUserRequestDto;
import com.cineworld.cinemetro.application.dto.UserDto;
import com.cineworld.cinemetro.application.mapper.UserMapper;
import com.cineworld.cinemetro.application.service.UserService;
import com.cineworld.cinemetro.domain.enums.UserRole;
import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.persistence.repository.UserRepository;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.cineworld.cinemetro.domain.enums.UserRole.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private RegisterUserRequestDto registerRequest;
    private UserDto userDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
                user = User.builder()
                        .id(1L)
                        .email("test@example.com")
                        .password("password123")
                        .role(UserRole.ADMIN)
                        .build();
        registerRequest = new RegisterUserRequestDto("test@example.com", "password123");
        userDto = new UserDto(1L, "test@example.com", CUSTOMER);
    }
    @Test
    void testCreateUser(){
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(userMapper.registerRequestToUser(registerRequest)).thenReturn(user);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.createUser(registerRequest);

        assertThat(result).isNotNull();
        assertThat(result.email()).isEqualTo("test@example.com");

        verify(userRepository).save(user);
        verify(passwordEncoder).encode("password123");
        
    }
    @Test
    void testCreateUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(registerRequest));

        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).email()).isEqualTo("test@example.com");
    }

    @Test
    void testDeleteUserById_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUserById_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUserById(1L));
    }

}
*/