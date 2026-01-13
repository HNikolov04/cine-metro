package com.cineworld.cinemetro.domain.exceptions.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order not found with ID: " + id);
    }
}