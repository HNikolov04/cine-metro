package com.cineworld.cinemetro.domain.exceptions.order;

public class OrderValidationException extends RuntimeException {

    public OrderValidationException(String message) {
        super(message);
    }
}
