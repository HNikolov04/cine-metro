package com.cineworld.cinemetro.integration;

import com.cineworld.cinemetro.application.dto.notification.CreateNotificationRequestDto;
import com.cineworld.cinemetro.application.dto.notification.NotificationResponseDto;
import com.cineworld.cinemetro.application.service.notification.NotificationService;
import com.cineworld.cinemetro.domain.enums.user.UserRole;
import com.cineworld.cinemetro.domain.model.user.User;
import com.cineworld.cinemetro.persistence.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class NotificationServiceITest {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Autowired
    public NotificationServiceITest(NotificationService notificationService,
                                    UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @Test
    void createAndFetchNotifications() {
        User user = User.builder()
                .email("notify@test.com")
                .password("pass")
                .role(UserRole.CUSTOMER)
                .build();
        User saved = userRepository.save(user);

        NotificationResponseDto created = notificationService.create(
                new CreateNotificationRequestDto(saved.getId(), "Title", "Message")
        );

        assertNotNull(created.id());
        List<NotificationResponseDto> notifications = notificationService.getForUser(saved.getId());
        assertEquals(1, notifications.size());
        assertFalse(notifications.getFirst().read());
    }
}
