
package com.umg.reservas.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> crearRespuesta(
            int estado,
            String error,
            String mensaje) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("estado", estado);
        respuesta.put("error", error);
        respuesta.put("mensaje", mensaje);

        return respuesta;
    }

    // ==========================================
    // ERROR 400 - DATOS INVALIDOS
    // ==========================================

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorNegocio(
            IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(crearRespuesta(
                        400,
                        "Solicitud incorrecta",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // ERROR 400 - VALIDACIONES DTO
    // ==========================================

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

    // ==========================================
    // ERROR 400 - PARAMETROS INVALIDOS
    // ==========================================

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Map<String, Object>> manejarParametrosInvalidos(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(crearRespuesta(
                        400,
                        "Solicitud incorrecta",
                        "Los parametros enviados son invalidos " +
                                "o estan incompletos"
                ));
    }

    // ==========================================
    // ERROR 404 - RECURSO NO ENCONTRADO
    // ==========================================

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(
            RecursoNoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(crearRespuesta(
                        404,
                        "Recurso no encontrado",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // ERROR 409 - CONFLICTO DE RESERVA
    // ==========================================

    @ExceptionHandler(ConflictoReservaException.class)
    public ResponseEntity<Map<String, Object>> manejarConflicto(
            ConflictoReservaException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(crearRespuesta(
                        409,
                        "Conflicto de reservacion",
                        ex.getMessage()
                ));
    }

    // ==========================================
    // ERROR 409 - RESTRICCIONES POSTGRESQL
    // ==========================================

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridad(
            DataIntegrityViolationException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(crearRespuesta(
                        409,
                        "Conflicto de base de datos",
                        "La operacion incumple una restriccion " +
                                "de integridad o disponibilidad"
                ));
    }

    // ==========================================
    // ERROR 500 - ERROR INESPERADO
    // ==========================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorInterno(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(crearRespuesta(
                        500,
                        "Error interno del servidor",
                        "Ocurrio un error inesperado"
                ));
    }
}
