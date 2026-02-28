package com.biblioteca.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad JPA mapeada a la tabla "autores".
 *
 * BUENAS PRÁCTICAS:
 * - @Getter: solo lectura pública de campos.
 * - @NoArgsConstructor(PROTECTED): JPA lo necesita, pero no lo exponemos.
 * - @AllArgsConstructor(PRIVATE): requerido por @Builder, pero restringido.
 * - Sin @Setter: mutaciones controladas vía métodos de negocio.
 */
@Entity
@Table(name = "autores")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 80)
    private String nacionalidad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<LibroEntity> libros;

    // ── Auditoría automática ──

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Métodos de negocio (en lugar de @Setter abierto) ──

    /**
     * Actualiza los datos modificables del autor.
     * Campos como id, createdAt, activo NO se modifican aquí.
     */
    public void actualizarDatos(String nombre, String apellido,
            String nacionalidad, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Soft Delete: marca al autor como inactivo.
     */
    public void desactivar() {
        this.activo = false;
    }
}
