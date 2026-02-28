package com.biblioteca.api.service;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import com.biblioteca.api.entity.AutorEntity;
import com.biblioteca.api.exception.DuplicateResourceException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.mapper.AutorMapper;
import com.biblioteca.api.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de los casos de uso de Autores.
 * Contiene TODA la lógica de negocio.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AutorServiceImpl implements AutorService {

    private final AutorRepository repository;
    private final AutorMapper mapper;

    /**
     * Registra un nuevo autor.
     * REGLA: No permite duplicados por nombre + apellido.
     */
    @Override
    public AutorResponseDto crear(AutorRequestDto dto) {
        if (repository.existsByNombreAndApellido(dto.nombre(), dto.apellido())) {
            throw new DuplicateResourceException("Autor", dto.nombre() + " " + dto.apellido());
        }

        AutorEntity entity = mapper.toEntity(dto);
        AutorEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca un autor por ID.
     * Lanza 404 si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public AutorResponseDto buscarPorId(Long id) {
        AutorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista autores activos con paginación.
     * Solo devuelve registros con activo = true.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AutorResponseDto> listar(Pageable pageable) {
        return repository.findByActivoTrue(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Actualiza un autor existente.
     * Usa el método de negocio actualizarDatos() de la Entity.
     */
    @Override
    public AutorResponseDto actualizar(Long id, AutorRequestDto dto) {
        AutorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));

        mapper.updateEntity(entity, dto);
        AutorEntity updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Soft Delete: marca al autor como inactivo.
     * NO elimina físicamente el registro.
     */
    @Override
    public void eliminar(Long id) {
        AutorEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", id));

        entity.desactivar();
        repository.save(entity);
    }
}
