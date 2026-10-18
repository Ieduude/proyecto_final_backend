package com.example.alquilerherramientas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoDisponibleException.class)
    public ResponseEntity<String> manejarRecursoNoDisponible(
            RecursoNoDisponibleException excepcion) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(excepcion.getMessage());
    }
}