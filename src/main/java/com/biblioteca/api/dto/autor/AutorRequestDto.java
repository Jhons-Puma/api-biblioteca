package com.biblioteca.api.dto.autor;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar un Autor.
 * Usa Java Record (inmutable) con validaciones Jakarta.
 */
public record AutorRequestDto(

        @NotBlank(message = "El nombre es obligatorio") String nombre,

        @NotBlank(message = "El apellido es obligatorio") String apellido,

        String nacionalidad,

        LocalDate fechaNacimiento) {
}
