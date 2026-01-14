package com.cineworld.cinemetro.domain.enums.order;

public enum OrderStatus {
    PENDING,     // Created but not paid
    CONFIRMED,   // Payment successful
    CANCELLED    // User/admin cancelled
}