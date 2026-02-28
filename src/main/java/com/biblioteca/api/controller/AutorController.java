package com.biblioteca.api.controller;

import com.biblioteca.api.dto.autor.AutorRequestDto;
import com.biblioteca.api.dto.autor.AutorResponseDto;
import com.biblioteca.api.dto.libro.LibroResponseDto;
import com.biblioteca.api.service.AutorService;
import com.biblioteca.api.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso Autor.
 * Expone endpoints CRUD + listado de libros por autor.
 *
 * Base path: /api/v1/autores
 */
@RestController
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final LibroService libroService;

    // ==========================================
    // POST - Crear autor
    // ==========================================

    @PostMapping
    public ResponseEntity<AutorResponseDto> crear(
            @RequestBody @Valid AutorRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(autorService.crear(dto));
    }

    // ==========================================
    // GET - Buscar autor por ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    // ==========================================
    // GET - Listar autores (paginado)
    // ==========================================

    @GetMapping
    public ResponseEntity<Page<AutorResponseDto>> listar(Pageable pageable) {
        return ResponseEntity.ok(autorService.listar(pageable));
    }

    // ==========================================
    // PUT - Actualizar autor
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDto> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid AutorRequestDto dto) {
        return ResponseEntity.ok(autorService.actualizar(id, dto));
    }

    // ==========================================
    // DELETE - Soft Delete
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==========================================
    // GET - Libros de un autor
    // ==========================================

    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroResponseDto>> listarLibros(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.listarPorAutor(id));
    }
}
