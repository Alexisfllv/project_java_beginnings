# Documentación del Servicio de Gestión de Comidas y Categorías

## Tipo de Servicio
**Servicio RESTful** para la gestión de comidas y categorías, construido con **Spring Boot**. Ofrece operaciones CRUD, validación de datos, paginación, y manejo estructurado de errores.

## Tecnologías y Herramientas
- **Spring Boot 3+**: Framework principal.
- **Spring Data JPA**: Acceso a datos y repositorios.
- **Hibernate**: ORM para mapeo objeto-relacional.
- **Lombok**: Reducción de código boilerplate (constructores, getters/setters).
- **MapStruct**: Mapeo entre entidades y DTOs.
- **Jakarta Validation**: Validación de campos en DTOs.
- **Spring MVC**: Arquitectura REST.
- **Swagger (OpenAPI 3)**: Documentación de endpoints.
- **Base de Datos Relacional**: Configuración mediante JPA (MySQL, PostgreSQL, etc.).

## Patrones de Diseño Aplicados
1. **MVC (Modelo-Vista-Controlador)**:
   - **Controladores**: `CategoriaController`, `ComidaController`.
   - **Modelos**: `Categoria`, `Comida`.
   - **Vista**: JSON (DTOs como capa de presentación).

2. **DTO (Data Transfer Object)**:
   - Separación entre entidades JPA (`Categoria`, `Comida`) y estructuras de transferencia (`CategoriaRequestDTO`, `ComidaResponseDTO`).

3. **Repositorio**:
   - Interfaces `CategoriaRepo`, `ComidaRepo` extienden `JpaRepository` para operaciones CRUD.

4. **Mapper**:
   - Conversión automática entre entidades y DTOs con `CategoriaMapper`, `ComidaMapper` (MapStruct).

5. **Singleton**:
   - Servicios y repositorios gestionados como beans de Spring (`@Service`, `@Repository`).

6. **Strategy**:
   - Manejo de excepciones mediante `GlobalExceptionHandler` con estrategias por tipo de error.

7. **Factory**:
   - `PaginaRespuestaDTO` construye respuestas paginadas desde un `Page` de Spring.

## Componentes Principales

### Entidades JPA
- **`Categoria`**: 
  - Campos: `id`, `nombre`.
  - Relación `@OneToMany` implícita con `Comida`.
- **`Comida`**:
  - Campos: `id`, `nombre`, `cantidad`, `peso`, `fechaInicio`, `fechaFin`.
  - Relación `@ManyToOne` con `Categoria`.

### DTOs
- **Request DTOs**: 
  - `CategoriaRequestDTO`, `ComidaRequestDTO` (validaciones con `@NotBlank`, `@Size`).
- **Response DTOs**: 
  - `CategoriaResponseDTO`, `ComidaResponseDTO` (incluye DTOs anidados).
- **Paginación**: 
  - `PaginaRespuestaDTO<T>` envuelve resultados paginados.

### Capa de Servicio
- **`CategoriaServiceImpl`**:
  - Lógica CRUD para categorías.
- **`ComidaServiceImpl`**:
  - Operaciones CRUD + paginación + filtrado por peso.
  - Gestión automática de fechas (`LocalDateTime`).

### Controladores
- **Endpoints**:
  - `GET /vinculo/categorias/listar`: Lista todas las categorías.
  - `POST /vinculo/comidas/registrar`: Crea una comida con validación.
  - `GET /vinculo/comidas/buscar/mayorpeso/{peso}/listar-page`: Filtra comidas por peso (paginado).

### Manejo de Excepciones
- **`GlobalExceptionHandler`**:
  - Maneja errores HTTP: 400 (validación), 404 (no encontrado), 500 (interno).
  - Respuestas estandarizadas con mensajes y códigos.
- **Excepciones Personalizadas**:
  - `RecursoNoEncontradoException`: Lanzada cuando un recurso no existe.
  - `DatosInvalidosException`: Errores de validación de negocio.

## Calificación como Servicio
**9/10**

### Puntos Fuertes
- ✅ Arquitectura limpia (capas separadas).
- ✅ Validación robusta en DTOs y servicios.
- ✅ Paginación integrada.
- ✅ Documentación Swagger básica.
- ✅ Manejo centralizado de errores.
- ✅ Uso eficiente de MapStruct y Lombok.

### Mejoras Potenciales
- 🔄 Agregar autenticación/autorización (JWT, OAuth2).
- 🔄 Implementar caché (Ej: `@Cacheable` en servicios).
- 🔄 Pruebas unitarias/integración (JUnit, Mockito).
- 🔄 Logging detallado (SLF4J).
- 🔄 Configuración de base de datos en `application.properties`.

## Conclusión
Este servicio es un ejemplo sólido de una API REST con Spring Boot, ideal para aplicaciones de gestión de alimentos. Combina buenas prácticas (DTOs, validación, paginación) con herramientas modernas (MapStruct, Lombok). ¡Listo para escalar con mejoras como seguridad y caching!
