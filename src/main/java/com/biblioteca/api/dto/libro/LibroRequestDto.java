package com.biblioteca.api.dto.libro;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO de entrada para crear o actualizar un Libro.
 * Usa Java Record (inmutable) con validaciones Jakarta.
 */
public record LibroRequestDto(

        @NotBlank(message = "El título es obligatorio") String titulo,

        @Pattern(regexp = "^[0-9]{10,13}$", message = "El ISBN debe tener entre 10 y 13 dígitos numéricos") String isbn,

        String genero,

        Integer anioPublicacion,

        @Min(value = 1, message = "La cantidad de páginas debe ser al menos 1") Integer numPaginas,

        @NotNull(message = "El ID del autor es obligatorio") Long autorId) {
}
