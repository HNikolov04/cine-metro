package com.cineworld.cinemetro.domain.exceptions.notification;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Notification not found with id: " + id);
    }
}
