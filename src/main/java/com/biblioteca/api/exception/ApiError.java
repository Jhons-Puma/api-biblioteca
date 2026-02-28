package com.biblioteca.api.exception;

import java.time.LocalDateTime;

/**
 * Record tipado para respuestas de error de la API.
 * Reemplaza los Map<String, Object> genéricos.
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path) {
}
