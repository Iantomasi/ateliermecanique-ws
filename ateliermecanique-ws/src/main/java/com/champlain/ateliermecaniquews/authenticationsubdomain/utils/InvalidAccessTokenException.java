package com.champlain.ateliermecaniquews.authenticationsubdomain.utils;

public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
