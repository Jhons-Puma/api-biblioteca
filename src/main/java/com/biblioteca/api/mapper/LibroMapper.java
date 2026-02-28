package com.biblioteca.api.mapper;

import com.biblioteca.api.dto.libro.LibroRequestDto;
import com.biblioteca.api.dto.libro.LibroResponseDto;
import com.biblioteca.api.entity.AutorEntity;
import com.biblioteca.api.entity.LibroEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper centralizado para conversiones entre Libro DTOs y Entity.
 * Responsabilidad única: transformar datos entre capas (SRP).
 */
@Component
public class LibroMapper {

    /**
     * Convierte RequestDto → Entity (para creación).
     * Requiere la AutorEntity ya resuelta desde el Service.
     */
    public LibroEntity toEntity(LibroRequestDto dto, AutorEntity autor) {
        return LibroEntity.builder()
                .titulo(dto.titulo())
                .isbn(dto.isbn())
                .genero(dto.genero())
                .anioPublicacion(dto.anioPublicacion())
                .numPaginas(dto.numPaginas())
                .autor(autor)
                .build();
    }

    /**
     * Convierte Entity → ResponseDto (para respuestas al cliente).
     * Incluye el nombre completo del autor denormalizado.
     */
    public LibroResponseDto toResponse(LibroEntity entity) {
        String autorNombre = entity.getAutor().getNombre()
                + " " + entity.getAutor().getApellido();

        return new LibroResponseDto(
                entity.getId(),
                entity.getTitulo(),
                entity.getIsbn(),
                entity.getGenero(),
                entity.getAnioPublicacion(),
                entity.getNumPaginas(),
                entity.getActivo(),
                entity.getAutor().getId(),
                autorNombre,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    /**
     * Actualiza una Entity existente con los datos del DTO.
     * Usa el método de negocio de la Entity (sin setters).
     */
    public void updateEntity(LibroEntity entity, LibroRequestDto dto, AutorEntity autor) {
        entity.actualizarDatos(
                dto.titulo(),
                dto.isbn(),
                dto.genero(),
                dto.anioPublicacion(),
                dto.numPaginas(),
                autor);
    }
}
