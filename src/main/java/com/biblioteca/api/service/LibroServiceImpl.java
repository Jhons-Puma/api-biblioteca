package com.biblioteca.api.service;

import com.biblioteca.api.dto.libro.LibroRequestDto;
import com.biblioteca.api.dto.libro.LibroResponseDto;
import com.biblioteca.api.entity.AutorEntity;
import com.biblioteca.api.entity.LibroEntity;
import com.biblioteca.api.exception.DuplicateResourceException;
import com.biblioteca.api.exception.ResourceNotFoundException;
import com.biblioteca.api.mapper.LibroMapper;
import com.biblioteca.api.repository.AutorRepository;
import com.biblioteca.api.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de los casos de uso de Libros.
 * Contiene TODA la lógica de negocio.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final LibroMapper mapper;

    /**
     * Registra un nuevo libro.
     * REGLAS:
     * - El autor debe existir.
     * - El ISBN no puede estar duplicado (si se proporciona).
     */
    @Override
    public LibroResponseDto crear(LibroRequestDto dto) {
        // Validar que el autor existe
        AutorEntity autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor", dto.autorId()));

        // Validar ISBN único (si se proporciona)
        if (dto.isbn() != null && libroRepository.existsByIsbn(dto.isbn())) {
            throw new DuplicateResourceException("Libro (ISBN)", dto.isbn());
        }

        LibroEntity entity = mapper.toEntity(dto, autor);
        LibroEntity saved = libroRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca un libro por ID.
     * Lanza 404 si no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public LibroResponseDto buscarPorId(Long id) {
        LibroEntity entity = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista libros activos con paginación.
     * Solo devuelve registros con activo = true.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LibroResponseDto> listar(Pageable pageable) {
        return libroRepository.findByActivoTrue(pageable)
                .map(mapper::toResponse);
    }

    /**
     * Actualiza un libro existente.
     * Valida que el nuevo autor (si cambia) exista.
     */
    @Override
    public LibroResponseDto actualizar(Long id, LibroRequestDto dto) {
        LibroEntity entity = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));

        AutorEntity autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor", dto.autorId()));

        mapper.updateEntity(entity, dto, autor);
        LibroEntity updated = libroRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Soft Delete: marca el libro como inactivo.
     * NO elimina físicamente el registro.
     */
    @Override
    public void eliminar(Long id) {
        LibroEntity entity = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro", id));

        entity.desactivar();
        libroRepository.save(entity);
    }

    /**
     * Lista los libros activos de un autor específico.
     * Lanza 404 si el autor no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> listarPorAutor(Long autorId) {
        // Verificar que el autor existe
        if (!autorRepository.existsById(autorId)) {
            throw new ResourceNotFoundException("Autor", autorId);
        }

        return libroRepository.findByAutorIdAndActivoTrue(autorId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
