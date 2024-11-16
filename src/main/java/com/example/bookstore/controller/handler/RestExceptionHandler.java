package com.example.bookstore.controller.handler;

import com.example.bookstore.dto.response.ErrorResponseDto;
import com.example.bookstore.exception.EmailAlreadyExistsException;
import com.example.bookstore.exception.InvalidBookGenreException;
import com.example.bookstore.exception.InvalidBookQuantityException;
import com.example.bookstore.exception.InvalidPriceRangeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.of(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookQuantityException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidBookQuantityException(InvalidBookQuantityException e) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(e.getMessage()));
    }

    @ExceptionHandler(InvalidBookGenreException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidBookGenreException(InvalidBookGenreException e) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(e.getMessage()));
    }

    @ExceptionHandler(InvalidPriceRangeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPriceRangeException(InvalidPriceRangeException e) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(e.getMessage()));
    }
}
