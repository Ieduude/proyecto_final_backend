package com.example.alquilerherramientas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresValidacion(
            MethodArgumentNotValidException excepcion) {

        Map<String, String> errores = new LinkedHashMap<>();

        excepcion.getBindingResult().getFieldErrors().forEach(error ->
                errores.putIfAbsent(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", 400);
        respuesta.put("mensaje", "Los datos enviados no son válidos");
        respuesta.put("errores", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException excepcion) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", 404);
        respuesta.put("mensaje", excepcion.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(RecursoNoDisponibleException.class)
    public ResponseEntity<String> manejarRecursoNoDisponible(
            RecursoNoDisponibleException excepcion) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(excepcion.getMessage());
    }

    @ExceptionHandler(FechasInvalidasException.class)
    public ResponseEntity<String> manejarFechasInvalidas(
            FechasInvalidasException excepcion) {

        return ResponseEntity.badRequest()
                .body(excepcion.getMessage());
    }

    @ExceptionHandler(DevolucionYaRegistradaException.class)
    public ResponseEntity<String> manejarDevolucionDuplicada(
            DevolucionYaRegistradaException excepcion) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(excepcion.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarArgumentoInvalido(
            IllegalArgumentException excepcion) {

        return ResponseEntity.badRequest()
                .body(excepcion.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manejarErrorNoEncontrado(
            RuntimeException excepcion) {

        if ("La herramienta no existe".equals(excepcion.getMessage())
                || "El alquiler no existe".equals(excepcion.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(excepcion.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error interno");
    }
}