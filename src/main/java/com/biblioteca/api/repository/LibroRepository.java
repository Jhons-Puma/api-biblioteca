package com.biblioteca.api.repository;

import com.biblioteca.api.entity.LibroEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad LibroEntity.
 * Métodos derivados filtran solo registros activos (Soft Delete).
 */
@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {

    List<LibroEntity> findByActivoTrue();

    Page<LibroEntity> findByActivoTrue(Pageable pageable);

    List<LibroEntity> findByAutorIdAndActivoTrue(Long autorId);

    boolean existsByIsbn(String isbn);
}
