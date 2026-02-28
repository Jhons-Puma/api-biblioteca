package com.biblioteca.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entidad JPA mapeada a la tabla "libros".
 *
 * BUENAS PRÁCTICAS:
 * - Sin @Setter: mutaciones controladas vía métodos de negocio.
 * - @ManyToOne(LAZY): evita N+1 queries por defecto.
 * - @Builder.Default: asegura valor por defecto de 'activo' al usar Builder.
 */
@Entity
@Table(name = "libros")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LibroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(length = 13, unique = true)
    private String isbn;

    @Column(length = 80)
    private String genero;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "num_paginas")
    private Integer numPaginas;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private AutorEntity autor;

    // ── Auditoría automática ──

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Métodos de negocio ──

    /**
     * Actualiza los datos modificables del libro.
     * Campos como id, createdAt, activo NO se modifican aquí.
     */
    public void actualizarDatos(String titulo, String isbn, String genero,
            Integer anioPublicacion, Integer numPaginas,
            AutorEntity autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.numPaginas = numPaginas;
        this.autor = autor;
    }

    /**
     * Soft Delete: marca el libro como inactivo.
     */
    public void desactivar() {
        this.activo = false;
    }
}
