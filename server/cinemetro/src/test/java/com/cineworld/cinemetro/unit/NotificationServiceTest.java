package com.cineworld.cinemetro.unit;

import com.cineworld.cinemetro.application.dto.notification.CreateNotificationRequestDto;
import com.cineworld.cinemetro.application.dto.notification.NotificationResponseDto;
import com.cineworld.cinemetro.application.service.notification.NotificationService;
import com.cineworld.cinemetro.domain.exceptions.user.UserNotFoundException;
import com.cineworld.cinemetro.domain.model.notification.Notification;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.notification.NotificationRepository;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationService notificationService;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();
    }

    @Test
    void create_success() {
        CreateNotificationRequestDto request = new CreateNotificationRequestDto(1L, "Title", "Message");
        Notification notification = Notification.builder()
                .id(10L)
                .user(user)
                .title("Title")
                .message("Message")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        NotificationResponseDto response = notificationService.create(request);

        assertEquals(10L, response.id());
        assertEquals(1L, response.userId());
    }

    @Test
    void getForUser_notFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> notificationService.getForUser(1L));

        assertEquals("User not found with id: 1", ex.getMessage());
    }

    @Test
    void markRead_updatesState() {
        Notification notification = Notification.builder()
                .id(10L)
                .user(user)
                .title("Title")
                .message("Message")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(notificationRepository.findByIdAndUser_Id(10L, 1L)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        NotificationResponseDto response = notificationService.markRead(10L, "test@example.com");

        assertEquals(true, response.read());
    }
}
