package com.example.bookstore.exception;

public class InvalidBookGenreException extends RuntimeException {
    public InvalidBookGenreException(String message) {
        super(message);
    }
}
