# 🍽️ Sistema de Gestión de Alimentos 

## 1. Configuración del Proyecto

### 1.1 Dependencias (pom.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.3</version>
    </parent>

    <dependencies>
        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- MySQL Connector -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- MapStruct para mapeo de objetos -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>1.6.3</version>
        </dependency>
    </dependencies>
</project>
```

### 1.2 Configuración de Aplicación (application.properties)
```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/beginning_java
spring.datasource.username=root
spring.datasource.password=deadmau5

# Configuración JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 2. Modelos

### 2.1 Modelo Categoria
```java
@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "categoria_nombre")
    private String nombre;
}
```

### 2.2 Modelo Comida
```java
@Entity
@Table(name = "comidas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comida_id")
    private Integer id;

    @Column(name = "comida_nombre", nullable = false)
    private String nombre;

    @Column(name = "producto_cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
```

## 3. DTOs (Data Transfer Objects)

### 3.1 CategoriaDTO
```java
public record CategoriaDTO(
    Integer id,
    String nombre
) {}
```

### 3.2 ComidaDTO
```java
public record ComidaDTO(
    Integer id,
    String nombre,
    Integer cantidad,
    CategoriaDTO categoria
) {}
```

## 4. Repositorios

### 4.1 CategoriaRepository
```java
@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {
    // Métodos de búsqueda personalizados
    Optional<Categoria> findByNombre(String nombre);
}
```

### 4.2 ComidaRepository
```java
@Repository
public interface ComidaRepo extends JpaRepository<Comida, Integer> {
    // Búsqueda por categoría
    List<Comida> findByCategoriaId(Integer categoriaId);

    // Búsqueda paginada
    Page<Comida> findByCantidadGreaterThan(Integer cantidad, Pageable pageable);
}
```

## 5. Mappers

### 5.1 CategoriaMapper
```java
@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria categoria);
    Categoria toEntity(CategoriaDTO categoriaDTO);
}
```

### 5.2 ComidaMapper
```java
@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface ComidaMapper {
    ComidaDTO toDTO(Comida comida);
    Comida toEntity(ComidaDTO comidaDTO);
}
```

## 6. Servicios

### 6.1 CategoriaService
```java
public interface CategoriaService {
    List<CategoriaDTO> listarTodas();
    CategoriaDTO crear(CategoriaDTO categoriaDTO);
    CategoriaDTO actualizar(Integer id, CategoriaDTO categoriaDTO);
    void eliminar(Integer id);
}
```

### 6.2 Implementación de CategoriaService
```java
@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepo categoriaRepo;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepo.findAll().stream()
            .map(categoriaMapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO crear(CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        categoria = categoriaRepo.save(categoria);
        return categoriaMapper.toDTO(categoria);
    }
}
```

## 7. Controladores

### 7.1 CategoriaController
```java
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarTodas();
    }

    @PostMapping
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaService.crear(categoriaDTO);
    }
}
```

## 8. Manejo de Excepciones

### 8.1 Excepciones Personalizadas
```java
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(), 
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

## 9. Estructura de Carpetas

```
src/main/java/edu/com/beginnings/
├── controller/
│   ├── CategoriaController.java
│   └── ComidaController.java
├── model/
│   ├── Categoria.java
│   └── Comida.java
├── repository/
│   ├── CategoriaRepo.java
│   └── ComidaRepo.java
├── service/
│   ├── CategoriaService.java
│   └── ComidaServiceImpl.java
└── dto/
    ├── CategoriaDTO.java
    └── ComidaDTO.java
```

## 10. Consideraciones Finales

- Utiliza Java 21
- Arquitectura por capas
- Validaciones integradas
- Mapeo automático con MapStruct
- Manejo de excepciones centralizado
