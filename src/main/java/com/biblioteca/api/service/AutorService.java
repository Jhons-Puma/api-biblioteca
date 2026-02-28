package com.biblioteca.api.service;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Contrato de la capa de servicio para Autores.
 * Define los casos de uso del dominio.
 */
public interface AutorService {

    AutorResponseDto crear(AutorRequestDto dto);

    AutorResponseDto buscarPorId(Long id);

    Page<AutorResponseDto> listar(Pageable pageable);

    AutorResponseDto actualizar(Long id, AutorRequestDto dto);

    void eliminar(Long id);
}
