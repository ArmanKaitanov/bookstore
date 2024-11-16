package com.example.bookstore.exception;

public class InvalidBookQuantityException extends RuntimeException {
    public InvalidBookQuantityException(String message) {
        super(message);
    }
}
