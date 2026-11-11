
package com.etitc.crm.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // DATOS INVÁLIDOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errores = new LinkedHashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(
                    error.getField(),
                    error.getDefaultMessage()
            );
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("estado", 400);
        respuesta.put("error", "Datos inválidos");
        respuesta.put("detalles", errores);

        return ResponseEntity.badRequest().body(respuesta);
    }

    // JSON INCORRECTO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJson(
            HttpMessageNotReadableException ex
    ) {

        return construirRespuesta(
                HttpStatus.BAD_REQUEST,
                "El cuerpo JSON está vacío o tiene un formato incorrecto"
        );
    }

    // RESTRICCIONES DE INTEGRIDAD DE LA BASE DE DATOS
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridad(
            DataIntegrityViolationException ex
    ) {

        return construirRespuesta(
                HttpStatus.CONFLICT,
                "No se puede completar la operación porque el registro está relacionado con otros datos"
        );
    }

    // ERRORES HTTP GENERADOS POR EL SERVICE
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarEstado(
            ResponseStatusException ex
    ) {

        HttpStatus status = HttpStatus.valueOf(
                ex.getStatusCode().value()
        );

        return construirRespuesta(
                status,
                ex.getReason() != null
                        ? ex.getReason()
                        : "Error en la solicitud"
        );
    }

    // ERRORES INTERNOS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorInterno(
            Exception ex
    ) {

        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor"
        );
    }

    // CONSTRUIR RESPUESTA JSON
    private ResponseEntity<Map<String, Object>> construirRespuesta(
            HttpStatus status,
            String mensaje
    ) {

        Map<String, Object> respuesta = new LinkedHashMap<>();

        respuesta.put("fecha", LocalDateTime.now());
        respuesta.put("estado", status.value());
        respuesta.put("error", status.getReasonPhrase());
        respuesta.put("mensaje", mensaje);

        return ResponseEntity.status(status).body(respuesta);
    }
}
