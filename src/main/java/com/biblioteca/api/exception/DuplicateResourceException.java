package com.biblioteca.api.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso duplicado.
 * Mapea a HTTP 409 Conflict.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String recurso, String valor) {
        super(String.format("Ya existe un %s con el valor '%s'", recurso, valor));
    }
}
