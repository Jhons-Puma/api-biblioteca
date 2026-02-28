-- =====================================================
-- SCRIPT SQL: Base de datos bd-biblioteca
-- Ejecutar en pgAdmin (PostgreSQL)
-- =====================================================

-- PASO 1: Crear la base de datos
-- (Ejecutar este bloque por separado en pgAdmin → Click derecho en Databases → Create)
-- O ejecutar en la consola SQL conectado a 'postgres':
-- CREATE DATABASE "bd-biblioteca";

-- =====================================================
-- PASO 2: Conectar a bd-biblioteca y ejecutar lo siguiente
-- =====================================================

-- Tabla AUTORES
CREATE TABLE autores (
    id               BIGSERIAL       PRIMARY KEY,
    nombre           VARCHAR(100)    NOT NULL,
    apellido         VARCHAR(100)    NOT NULL,
    nacionalidad     VARCHAR(80),
    fecha_nacimiento DATE,
    activo           BOOLEAN         NOT NULL DEFAULT TRUE,

    -- Auditoría
    created_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices autores
CREATE INDEX idx_autores_nombre ON autores (apellido, nombre);
CREATE INDEX idx_autores_activo ON autores (activo);

-- Tabla LIBROS
CREATE TABLE libros (
    id               BIGSERIAL       PRIMARY KEY,
    titulo           VARCHAR(255)    NOT NULL,
    isbn             VARCHAR(13)     UNIQUE,
    genero           VARCHAR(80),
    anio_publicacion INTEGER,
    num_paginas      INTEGER         CHECK (num_paginas > 0),
    activo           BOOLEAN         NOT NULL DEFAULT TRUE,

    -- FK → autores
    autor_id         BIGINT          NOT NULL,
    CONSTRAINT fk_libro_autor FOREIGN KEY (autor_id)
        REFERENCES autores(id),

    -- Auditoría
    created_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Índices libros
CREATE INDEX idx_libros_titulo ON libros (titulo);
CREATE INDEX idx_libros_isbn   ON libros (isbn);
CREATE INDEX idx_libros_autor  ON libros (autor_id);
CREATE INDEX idx_libros_activo ON libros (activo);

-- =====================================================
-- DATOS DE PRUEBA
-- =====================================================

INSERT INTO autores (nombre, apellido, nacionalidad, fecha_nacimiento)
VALUES
    ('Gabriel', 'García Márquez', 'Colombiana', '1927-03-06'),
    ('Mario', 'Vargas Llosa', 'Peruana', '1936-03-28'),
    ('Isabel', 'Allende', 'Chilena', '1942-08-02'),
    ('Jorge Luis', 'Borges', 'Argentina', '1899-08-24'),
    ('Pablo', 'Neruda', 'Chilena', '1904-07-12');

INSERT INTO libros (titulo, isbn, genero, anio_publicacion, num_paginas, autor_id)
VALUES
    ('Cien años de soledad', '9780307474728', 'Realismo mágico', 1967, 417, 1),
    ('El amor en los tiempos del cólera', '9780307389732', 'Romance', 1985, 368, 1),
    ('La ciudad y los perros', '9788420471839', 'Novela', 1963, 344, 2),
    ('La casa de los espíritus', '9780553383805', 'Realismo mágico', 1982, 433, 3),
    ('Ficciones', '9780802130303', 'Cuentos', 1944, 174, 4),
    ('Veinte poemas de amor', '9789500398282', 'Poesía', 1924, 64, 5);
