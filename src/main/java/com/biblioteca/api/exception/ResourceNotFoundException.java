package com.biblioteca.api.exception;

/**
 * Excepción lanzada cuando un recurso no se encuentra en la BD.
 * Mapea a HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(String.format("No se encontró %s con ID %d", recurso, id));
    }
}
