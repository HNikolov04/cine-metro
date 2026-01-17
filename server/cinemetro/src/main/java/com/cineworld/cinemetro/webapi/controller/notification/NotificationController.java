package com.cineworld.cinemetro.webapi.controller.notification;

import com.cineworld.cinemetro.application.dto.notification.CreateNotificationRequestDto;
import com.cineworld.cinemetro.application.dto.notification.NotificationResponseDto;
import com.cineworld.cinemetro.application.service.notification.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NotificationResponseDto> create(@Valid @RequestBody CreateNotificationRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<NotificationResponseDto>> getMyNotifications(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.getForEmail(userDetails.getUsername()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<NotificationResponseDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getForUser(userId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponseDto> markRead(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.markRead(id, userDetails.getUsername()));
    }
}
