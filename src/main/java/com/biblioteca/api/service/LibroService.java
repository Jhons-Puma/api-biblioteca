package com.biblioteca.api.service;

import com.biblioteca.api.dto.libro.LibroRequestDto;
import com.biblioteca.api.dto.libro.LibroResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Contrato de la capa de servicio para Libros.
 * Define los casos de uso del dominio.
 */
public interface LibroService {

    LibroResponseDto crear(LibroRequestDto dto);

    LibroResponseDto buscarPorId(Long id);

    Page<LibroResponseDto> listar(Pageable pageable);

    LibroResponseDto actualizar(Long id, LibroRequestDto dto);

    void eliminar(Long id);

    List<LibroResponseDto> listarPorAutor(Long autorId);
}
