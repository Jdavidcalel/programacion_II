
package com.umg.reservas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. ERRORES DE REGLAS DE NEGOCIO
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorNegocio(
            IllegalArgumentException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("estado", 400);
        respuesta.put("error", "Solicitud incorrecta");
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    // 2. ERRORES DE VALIDACION DE DATOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        Map<String, String> errores = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("estado", 400);
        respuesta.put("error", "Error de validacion");
        respuesta.put("detalles", errores);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }
}
