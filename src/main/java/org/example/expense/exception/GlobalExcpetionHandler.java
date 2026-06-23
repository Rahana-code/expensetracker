package org.example.expense.exception;


import org.example.expense.dto.ErrorResponseDTO;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleNotFound(
            ResourceNotFoundException ex) {

        ErrorResponseDTO error =
                ErrorResponseDTO.builder()
                        .message(ex.getMessage())
                        .status(404)
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleValidation(
            MethodArgumentNotValidException ex) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        ErrorResponseDTO error =
                ErrorResponseDTO.builder()
                        .message(message)
                        .status(400)
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST);
    }
}

