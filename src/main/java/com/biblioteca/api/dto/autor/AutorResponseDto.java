package com.biblioteca.api.dto.autor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de salida para devolver los datos de un Autor.
 * Incluye campos de auditoría visibles al cliente.
 */
public record AutorResponseDto(
        Long id,
        String nombre,
        String apellido,
        String nacionalidad,
        LocalDate fechaNacimiento,
        Boolean activo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
