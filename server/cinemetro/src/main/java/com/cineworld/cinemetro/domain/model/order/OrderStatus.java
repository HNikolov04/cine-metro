package com.cineworld.cinemetro.domain.model.order;

public enum OrderStatus {
    PENDING,     // Created but not paid
    CONFIRMED,   // Payment successful
    CANCELLED    // User/admin cancelled
}