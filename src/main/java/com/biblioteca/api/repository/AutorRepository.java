package com.biblioteca.api.repository;

import com.biblioteca.api.entity.AutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad AutorEntity.
 * Métodos derivados filtran solo registros activos (Soft Delete).
 */
@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    List<AutorEntity> findByActivoTrue();

    Page<AutorEntity> findByActivoTrue(Pageable pageable);

    boolean existsByNombreAndApellido(String nombre, String apellido);
}
