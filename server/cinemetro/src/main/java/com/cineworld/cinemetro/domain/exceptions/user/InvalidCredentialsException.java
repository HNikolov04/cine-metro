package com.cineworld.cinemetro.domain.exceptions.user;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
