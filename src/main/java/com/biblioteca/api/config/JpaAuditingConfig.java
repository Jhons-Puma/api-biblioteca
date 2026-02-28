package com.biblioteca.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Habilita la auditoría automática de JPA.
 * Permite que @CreatedDate y @LastModifiedDate se llenen automáticamente.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
