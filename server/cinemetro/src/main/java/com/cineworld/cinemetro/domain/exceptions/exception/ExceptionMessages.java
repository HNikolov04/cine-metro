package com.cineworld.cinemetro.domain.exceptions.exception;

public final class ExceptionMessages {

    private ExceptionMessages() {} // prevent instantiation

    public static final class Order {
        public static final String NOT_FOUND = "Order not found with ID: %d";
    }

    // Later: add inner classes for Product, Ticket, User, etc.
}