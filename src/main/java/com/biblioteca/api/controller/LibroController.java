package com.biblioteca.api.controller;

import com.biblioteca.api.dto.libro.LibroRequestDto;
import com.biblioteca.api.dto.libro.LibroResponseDto;
import com.biblioteca.api.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para el recurso Libro.
 * Expone endpoints CRUD con paginación.
 *
 * Base path: /api/v1/libros
 */
@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    // ==========================================
    // POST - Crear libro
    // ==========================================

    @PostMapping
    public ResponseEntity<LibroResponseDto> crear(
            @RequestBody @Valid LibroRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(libroService.crear(dto));
    }

    // ==========================================
    // GET - Buscar libro por ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.buscarPorId(id));
    }

    // ==========================================
    // GET - Listar libros (paginado)
    // ==========================================

    @GetMapping
    public ResponseEntity<Page<LibroResponseDto>> listar(Pageable pageable) {
        return ResponseEntity.ok(libroService.listar(pageable));
    }

    // ==========================================
    // PUT - Actualizar libro
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDto> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid LibroRequestDto dto) {
        return ResponseEntity.ok(libroService.actualizar(id, dto));
    }

    // ==========================================
    // DELETE - Soft Delete
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
