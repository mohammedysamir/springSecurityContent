package com.security.demo.exception;

public class NotFoundException extends RuntimeException {
    String message;

    @Override
    public String getMessage() {
        return this.message;
    }

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
