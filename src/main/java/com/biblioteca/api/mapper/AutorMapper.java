package com.biblioteca.api.mapper;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import com.biblioteca.api.entity.AutorEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper centralizado para conversiones entre Autor DTOs y Entity.
 * Responsabilidad única: transformar datos entre capas (SRP).
 */
@Component
public class AutorMapper {

    /**
     * Convierte RequestDto → Entity (para creación).
     * No setea ID ni campos de auditoría.
     */
    public AutorEntity toEntity(AutorRequestDto dto) {
        return AutorEntity.builder()
                .nombre(dto.nombre())
                .apellido(dto.apellido())
                .nacionalidad(dto.nacionalidad())
                .fechaNacimiento(dto.fechaNacimiento())
                .build();
    }

    /**
     * Convierte Entity → ResponseDto (para respuestas al cliente).
     */
    public AutorResponseDto toResponse(AutorEntity entity) {
        return new AutorResponseDto(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getNacionalidad(),
                entity.getFechaNacimiento(),
                entity.getActivo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Actualiza una Entity existente con los datos del DTO.
     * Usa el método de negocio de la Entity (sin setters).
     */
    public void updateEntity(AutorEntity entity, AutorRequestDto dto) {
        entity.actualizarDatos(
                dto.nombre(),
                dto.apellido(),
                dto.nacionalidad(),
                dto.fechaNacimiento());
    }
}
