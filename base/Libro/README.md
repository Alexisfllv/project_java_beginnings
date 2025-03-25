# Aplicación de Gestión de Libros con Spring Boot

## Descripción general

Esta aplicación es un servicio REST para la gestión de libros, desarrollada con Spring Boot y siguiendo una arquitectura multicapa. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una entidad Libro.

## Arquitectura

La aplicación sigue el patrón de arquitectura en capas:

1. **Capa de presentación**: Controladores REST
2. **Capa de servicio**: Lógica de negocio
3. **Capa de persistencia**: Repositorios y entidades JPA
4. **Capa de transferencia de datos**: DTOs y Mappers

## Componentes principales

### Entidad

```java
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "libro_titulo", length = 60, nullable = true)
    private String titulo;
    
    @Column(name = "libro_autor", length = 80, nullable = false)
    private String autor;
    
    @Column(name = "libro_fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;
}
```

### DTOs

```java
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class LibroDTO {
    private Integer id;
    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
}

@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class LibroResponseDTO {
    private Integer id;
    private String titulo;
    private String autor;
}
```

### Mapper

```java
@Mapper(componentModel = "spring")
public interface LibroMapper {
    // Entidad > DTO
    LibroDTO toDTO(Libro libro);
    
    // DTO > Entidad
    Libro toLibro(LibroDTO libroDTO);
    
    // Entidad > ResponseDTO
    LibroResponseDTO toResponseDTO(Libro libro);
    
    // ResponseDTO > Entidad
    Libro toLibro2(LibroResponseDTO libroResponseDTO);
}
```

### Repositorio

```java
@Repository
public interface LibroRepo extends JpaRepository<Libro, Integer> {
}
```

### Servicio

```java
public interface LibroService {
    // Operaciones de servicio
    List<LibroResponseDTO> listarLibrosdto();
    LibroResponseDTO buscarDtoResponse(Integer id);
    LibroResponseDTO guardarLibrodto(LibroDTO libroDTO);
    LibroResponseDTO modificarunlibrodto(LibroDTO libro, Integer id);
    void borrarLibro(Integer id);
    LibroDTO buscarDto(Integer id);
}
```

### Implementación del Servicio

```java
@Service 
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {
    
    // Inyección de dependencias
    private final LibroRepo repo;
    
    @Qualifier("libroMapper")
    private final LibroMapper libroMapper;
    
    @Override
    public List<LibroResponseDTO> listarLibrosdto() {
        List<Libro> libros = repo.findAll();
        
        return libros.stream()
                .map(libroMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public LibroResponseDTO buscarDtoResponse(Integer id) {
        Libro libro = repo.findById(id).get();
        return libroMapper.toResponseDTO(libro);
    }
    
    @Override
    public LibroResponseDTO guardarLibrodto(LibroDTO libroDTO) {
        Libro libro = libroMapper.toLibro(libroDTO);
        Libro save = repo.save(libro);
        return libroMapper.toResponseDTO(save);
    }
    
    @Override
    public LibroResponseDTO modificarunlibrodto(LibroDTO libroDTO, Integer id) {
        Libro libroExistente = repo.findById(id).get();
        libroExistente.setTitulo(libroDTO.getTitulo());
        libroExistente.setAutor(libroDTO.getAutor());
        libroExistente.setFechaPublicacion(libroDTO.getFechaPublicacion());
        
        libroExistente = repo.save(libroExistente);
        return libroMapper.toResponseDTO(libroExistente);
    }
    
    @Override
    public void borrarLibro(Integer id) {
        repo.deleteById(id);
    }
    
    @Override
    public LibroDTO buscarDto(Integer id) {
        Libro libro = repo.findById(id).orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        return libroMapper.toDTO(libro);
    }
}
```

### Controlador REST

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/base/libros")
public class LibroController {

    private final LibroService libroService;

    @GetMapping("/listado")
    public ResponseEntity<List<LibroResponseDTO>> listado() {
        return ResponseEntity.ok(libroService.listarLibrosdto());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<LibroResponseDTO> buscar(@PathVariable Integer id) {
        LibroResponseDTO libro = libroService.buscarDtoResponse(id);
        return ResponseEntity.ok(libro);
    }

    @PostMapping("/registrar")
    public ResponseEntity<LibroResponseDTO> registrar(@RequestBody LibroDTO libroDTO) {
        return ResponseEntity.ok(libroService.guardarLibrodto(libroDTO));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<LibroResponseDTO> modificar(@RequestBody LibroDTO libroDTO, @PathVariable Integer id) {
        LibroResponseDTO libro2 = libroService.modificarunlibrodto(libroDTO, id);
        return ResponseEntity.ok(libro2);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        libroService.borrarLibro(id);
        return ResponseEntity.ok().build();
    }
}
```

## API Endpoints

| Método HTTP | Endpoint | Descripción |
|-------------|----------|-------------|
| GET | /base/libros/listado | Obtiene todos los libros |
| GET | /base/libros/buscar/{id} | Busca un libro por ID |
| POST | /base/libros/registrar | Crea un nuevo libro |
| PUT | /base/libros/modificar/{id} | Actualiza un libro existente |
| DELETE | /base/libros/eliminar/{id} | Elimina un libro |

## Tecnologías utilizadas

- **Spring Boot**: Framework principal
- **Spring Data JPA**: Persistencia de datos
- **Lombok**: Reducción de código boilerplate
- **MapStruct**: Mapeo entre objetos

## Patrones de diseño

- **DTO (Data Transfer Object)**: Para transferencia de datos entre capas
- **Repository Pattern**: Para acceso a datos
- **Dependency Injection**: Inyección de dependencias mediante Spring
- **Service Layer**: Encapsulación de la lógica de negocio

## Diagrama de flujo

```
+----------------+     +----------------+     +----------------+
| LibroController|---->| LibroService   |---->| LibroRepo      |
+----------------+     +----------------+     +----------------+
        |                     |                       |
        |                     |                       |
        v                     v                       v
   (REST API)          (Lógica Negocio)         (Base de Datos)
                              |                       
                              v                       
                       +----------------+      +----------------+
                       | LibroMapper    |----->| Libro (Entity) |
                       +----------------+      +----------------+
                              |
                              v
                       +----------------+
                       | DTOs           |
                       +----------------+
```

## Configuración

La aplicación requiere una base de datos configurada en el archivo `application.properties` o `application.yml`.

## Cómo ejecutar

1. Clonar el repositorio
2. Configurar la base de datos
3. Ejecutar `mvn spring-boot:run`
4. Acceder a los endpoints a través de http://localhost:8080/base/libros/
