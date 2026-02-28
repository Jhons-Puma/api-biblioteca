package com.biblioteca.api.dto.libro;

import java.time.LocalDateTime;

/**
 * DTO de salida para devolver los datos de un Libro.
 * Incluye el nombre completo del autor y campos de auditoría.
 */
public record LibroResponseDto(
        Long id,
        String titulo,
        String isbn,
        String genero,
        Integer anioPublicacion,
        Integer numPaginas,
        Boolean activo,
        Long autorId,
        String autorNombreCompleto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
