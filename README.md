# 📚 API REST Biblioteca

API REST desarrollada con **Spring Boot 3.5** siguiendo una **arquitectura en capas**, principios **SOLID** y buenas prácticas de programación.

## 🛠️ Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.0 |
| Spring Data JPA | Incluido |
| PostgreSQL | 15+ |
| Lombok | Incluido |
| Jakarta Validation | Incluido |
| Maven | Wrapper incluido |

## 📐 Arquitectura

```
Controller → Service (Interface) → Repository → Entity
    ↕            ↕                                  ↕
   DTOs        Mapper                          Base de Datos
```

### Estructura del proyecto

```
com.biblioteca.api/
├── config/          → JpaAuditingConfig, CorsConfig
├── controller/      → AutorController, LibroController
├── dto/
│   ├── autor/       → AutorRequestDto, AutorResponseDto
│   └── libro/       → LibroRequestDto, LibroResponseDto
├── entity/          → AutorEntity, LibroEntity
├── exception/       → ApiError, GlobalExceptionHandler, excepciones custom
├── mapper/          → AutorMapper, LibroMapper
├── repository/      → AutorRepository, LibroRepository
└── service/         → Interfaces + Implementaciones
```

## 🗄️ Base de Datos

**Motor:** PostgreSQL  
**Nombre:** `bd-biblioteca`

### Diagrama de tablas

```
┌──────────────────────┐       ┌──────────────────────────┐
│       autores        │       │         libros           │
├──────────────────────┤       ├──────────────────────────┤
│ id (PK, BIGSERIAL)   │──┐    │ id (PK, BIGSERIAL)       │
│ nombre (VARCHAR 100)  │  │    │ titulo (VARCHAR 255)      │
│ apellido (VARCHAR 100)│  │    │ isbn (VARCHAR 13, UNIQUE)  │
│ nacionalidad          │  │    │ genero (VARCHAR 80)        │
│ fecha_nacimiento      │  │    │ anio_publicacion (INT)     │
│ activo (BOOLEAN)      │  │    │ num_paginas (INT, CHECK>0) │
│ created_at            │  │    │ activo (BOOLEAN)           │
│ updated_at            │  └───>│ autor_id (FK)              │
└──────────────────────┘       │ created_at                 │
                               │ updated_at                 │
                               └──────────────────────────┘
```

## 🔗 Endpoints

### Autores — `/api/v1/autores`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/autores` | Crear autor |
| `GET` | `/api/v1/autores` | Listar autores (paginado) |
| `GET` | `/api/v1/autores/{id}` | Buscar autor por ID |
| `PUT` | `/api/v1/autores/{id}` | Actualizar autor |
| `DELETE` | `/api/v1/autores/{id}` | Eliminar autor (soft delete) |
| `GET` | `/api/v1/autores/{id}/libros` | Libros de un autor |

### Libros — `/api/v1/libros`

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/libros` | Crear libro |
| `GET` | `/api/v1/libros` | Listar libros (paginado) |
| `GET` | `/api/v1/libros/{id}` | Buscar libro por ID |
| `PUT` | `/api/v1/libros/{id}` | Actualizar libro |
| `DELETE` | `/api/v1/libros/{id}` | Eliminar libro (soft delete) |

## ✅ Buenas Prácticas Implementadas

- **Entities sin `@Setter`** → Mutaciones controladas con métodos de negocio (`actualizarDatos()`, `desactivar()`)
- **`@NoArgsConstructor(PROTECTED)`** → JPA lo necesita, pero no se expone al exterior
- **`@AllArgsConstructor(PRIVATE)`** → Requerido por `@Builder`, pero restringido
- **DTOs como Records** → Inmutables por diseño
- **Soft Delete** → Campo `activo` en lugar de eliminación física
- **Auditoría automática** → `@CreatedDate` / `@LastModifiedDate` con JPA Auditing
- **Paginación** → `Page<>` + `Pageable` en listados
- **Errores tipados** → Record `ApiError` con `GlobalExceptionHandler`
- **Validaciones Jakarta** → `@NotBlank`, `@NotNull`, `@Pattern`, `@Min`
- **CORS externalizado** → Configurable desde `application.properties`

## 🚀 Instalación y Ejecución

### 1. Crear la base de datos

Abrir **pgAdmin** y crear la base de datos `bd-biblioteca`. Luego ejecutar el script `database_setup.sql` incluido en la raíz del proyecto.

### 2. Configurar credenciales

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bd-biblioteca
spring.datasource.username=postgres
spring.datasource.password=admin
```

### 3. Ejecutar

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

### 4. Probar con Postman

**Crear un autor:**
```json
POST http://localhost:8080/api/v1/autores

{
  "nombre": "Gabriel",
  "apellido": "García Márquez",
  "nacionalidad": "Colombiana",
  "fecha_nacimiento": "1927-03-06"
}
```

**Crear un libro:**
```json
POST http://localhost:8080/api/v1/libros

{
  "titulo": "Cien años de soledad",
  "isbn": "9780307474728",
  "genero": "Realismo mágico",
  "anio_publicacion": 1967,
  "num_paginas": 417,
  "autor_id": 1
}
```

## 📄 Licencia

Este proyecto es de uso personal y educativo.
