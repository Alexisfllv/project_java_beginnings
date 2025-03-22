# Documentación API de Productos Refrigerados

## Descripción General

Este proyecto implementa una API RESTful para la gestión de productos refrigerados utilizando Spring Boot. La aplicación permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre productos almacenados en una base de datos.

## Arquitectura

El proyecto sigue una arquitectura en capas:

- **Capa de Entidades**: Define el modelo de datos.
- **Capa de DTOs**: Objetos de transferencia de datos para las diferentes operaciones.
- **Capa de Mappers**: Conversión entre entidades y DTOs.
- **Capa de Servicios**: Lógica de negocio.
- **Capa de Controladores**: Endpoints de la API REST.

## Componentes

### Entidad Producto

```java
@Entity
@Table(name = "productos_refrigerados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    
    @Column(name = "producto_nombre", length = 60, nullable = false)
    private String nombre;
    
    @Column(name = "producto_cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "producto_peso", precision = 10, scale = 3, nullable = false)
    private BigDecimal peso;
    
    @Column(name = "producto_fecha_inicio", nullable = true)
    private LocalDateTime fechaInicio;
    
    @Column(name = "producto_fecha_fin", nullable = true)
    private LocalDateTime fechaFin;
    
    // Método comentado para pre-persistencia automática de fechas
}
```

**Características:**
- Utiliza Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) para reducir código boilerplate.
- Se mapea a la tabla `productos_refrigerados` en la base de datos.
- Incluye campos para:
  - ID (generado automáticamente)
  - Nombre del producto (limitado a 60 caracteres)
  - Cantidad
  - Peso (con precisión decimal)
  - Fechas de inicio y fin de refrigeración

### DTOs (Data Transfer Objects)

#### ProductoDTO

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Integer id;
    private String nombre;
    private Integer cantidad;
    private BigDecimal peso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
```

**Propósito:** DTO general para operaciones con todos los campos.

#### ProductoRequestDTO

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private Integer id;
    private String nombre;
    private Integer cantidad;
    private BigDecimal peso;
}
```

**Propósito:** DTO para peticiones que no requieren las fechas.

#### ProductoResponseDTO

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private Integer cantidad;
    private BigDecimal peso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
```

**Propósito:** DTO para respuestas, incluyendo todos los campos.

#### ProductoRequestRecordDTO (Java Record)

```java
public record ProductoRequestRecordDTO(
    Integer id,
    
    @NotBlank
    @Size(max = 60)
    String nombre,
    
    @NotNull
    @Min(1)
    Integer cantidad,
    
    @NotNull
    @DecimalMin(value = "0.001", inclusive = true, message = "🚨 🚨 🚨 peso mayor a 0.001")
    BigDecimal peso
) {}
```

**Propósito:** 
- DTO inmutable (Java Record) para solicitudes de creación.
- Incluye validaciones:
  - Nombre no puede estar vacío y máximo 60 caracteres
  - Cantidad debe ser al menos 1
  - Peso debe ser mayor a 0.001

### Mapper

```java
@Mapper(componentModel = "spring")
public interface ProductoMapper {
    // Conversiones entre Entidad y DTOs
    ProductoDTO toDTO(Producto producto);
    Producto toProducto(ProductoDTO productoDTO);
    
    ProductoResponseDTO toResponseDTO(Producto producto);
    Producto toProducto(ProductoResponseDTO productoResponseDTO);
    
    ProductoRequestDTO toRequestDTO(Producto producto);
    Producto toProducto(ProductoRequestDTO productoRequestDTO);
    
    // Conversiones para el Record DTO
    ProductoRequestRecordDTO toRequestRecordDTO(Producto producto);
    Producto toProducto(ProductoRequestRecordDTO productoRequestRecordDTO);
}
```

**Características:**
- Utiliza MapStruct (`@Mapper`) para mapeo automático entre objetos.
- Configurado como componente Spring.
- Define métodos bidireccionales para convertir entre todos los tipos de DTOs y la entidad Producto.

### Servicio

#### Interfaz

```java
public interface ProductoService {
    // Listado total
    List<ProductoResponseDTO> listarLibros();
    
    // Buscar producto
    ProductoResponseDTO buscarLibro(Integer id);
    
    // Registrar producto
    ProductoResponseDTO registrar(ProductoRequestRecordDTO productoRequestRecordDTO);
    
    // Modificar producto
    ProductoResponseDTO actualizar(ProductoDTO productoDTO, Integer id);
    
    // Eliminar
    void borrarProducto(Integer id);
}
```

#### Implementación

```java
@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoRepo productoRepo;
    private final ProductoMapper productoMapper;
    
    @Override
    public List<ProductoResponseDTO> listarLibros() {
        List<Producto> productos = productoRepo.findAll();
        return productos.stream()
                .map(productoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductoResponseDTO buscarLibro(Integer id) {
        Producto producto = productoRepo.findById(id).get();
        return productoMapper.toResponseDTO(producto);
    }
    
    @Override
    public ProductoResponseDTO registrar(ProductoRequestRecordDTO productoRequestRecordDTO) {
        Producto producto = productoMapper.toProducto(productoRequestRecordDTO);
        
        // Gestión de fechas
        if (producto.getFechaInicio() != null) {
            producto.setFechaInicio(producto.getFechaInicio());
        } else {
            producto.setFechaInicio(LocalDateTime.now());
        }
        
        if (producto.getFechaFin() != null) {
            producto.setFechaFin(producto.getFechaFin());
        } else {
            producto.setFechaFin(producto.getFechaInicio().plusDays(3));
        }
        
        Producto save = productoRepo.save(producto);
        return productoMapper.toResponseDTO(save);
    }
    
    @Override
    public ProductoResponseDTO actualizar(ProductoDTO productoDTO, Integer id) {
        Producto productoExiste = productoRepo.findById(id).get();
        
        productoExiste.setNombre(productoDTO.getNombre());
        productoExiste.setCantidad(productoDTO.getCantidad());
        productoExiste.setPeso(productoDTO.getPeso());
        
        // Actualización condicional de fechas
        if (productoDTO.getFechaInicio() != null) {
            productoExiste.setFechaInicio(productoDTO.getFechaInicio());
        }
        
        if (productoDTO.getFechaFin() != null) {
            productoExiste.setFechaFin(productoDTO.getFechaFin());
        }
        
        productoExiste = productoRepo.save(productoExiste);
        return productoMapper.toResponseDTO(productoExiste);
    }
    
    @Override
    public void borrarProducto(Integer id) {
        productoRepo.deleteById(id);
    }
}
```

**Características:**
- Utiliza inyección de dependencias con Lombok `@RequiredArgsConstructor`.
- Implementa operaciones CRUD:
  - **Listar**: Recupera todos los productos y los convierte a DTOs.
  - **Buscar**: Encuentra un producto por ID.
  - **Registrar**: Crea un nuevo producto con lógica para fechas automáticas.
  - **Actualizar**: Modifica un producto existente preservando fechas si no se especifican.
  - **Eliminar**: Borra un producto por ID.

### Controlador REST

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/base/productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarLibros());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.buscarLibro(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<ProductoResponseDTO> registrar(
            @Valid @RequestBody ProductoRequestRecordDTO productoRequestRecordDTO) {
        return ResponseEntity.ok(productoService.registrar(productoRequestRecordDTO));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductoResponseDTO> modificar(
            @RequestBody ProductoDTO productoDTO, @PathVariable Integer id) {
        ProductoResponseDTO modificado = productoService.actualizar(productoDTO, id);
        return ResponseEntity.ok(modificado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Características:**
- Define endpoints REST bajo la ruta base `/base/productos`.
- Implementa los métodos HTTP:
  - **GET** para listar y buscar
  - **POST** para registrar
  - **PUT** para modificar
  - **DELETE** para eliminar
- Usa `@Valid` para validar el DTO de registro.
- Retorna respuestas HTTP apropiadas (200 OK, 204 No Content).

## Funcionalidades Principales

### 1. Gestión Automática de Fechas
- Si no se especifica una fecha de inicio al crear un producto, se establece automáticamente la fecha actual.
- Si no se especifica una fecha de fin, se establece automáticamente 3 días después de la fecha de inicio.

### 2. Validación de Datos
- Nombre: No puede estar vacío y máximo 60 caracteres.
- Cantidad: Debe ser al menos 1.
- Peso: Debe ser mayor a 0.001.

### 3. Endpoints API

| Método HTTP | Ruta | Descripción |
|-------------|------|-------------|
| GET | `/base/productos/listar` | Obtener todos los productos |
| GET | `/base/productos/buscar/{id}` | Buscar producto por ID |
| POST | `/base/productos/registrar` | Crear nuevo producto |
| PUT | `/base/productos/modificar/{id}` | Actualizar producto existente |
| DELETE | `/base/productos/eliminar/{id}` | Eliminar producto |

## Tecnologías Utilizadas

- **Spring Boot**: Framework para desarrollo de aplicaciones Java.
- **Spring Data JPA**: Para persistencia y operaciones con base de datos.
- **Lombok**: Para reducir código boilerplate.
- **MapStruct**: Para mapeo entre objetos.
- **Bean Validation**: Para validación de datos.
- **Java Records**: Para DTOs inmutables.

## Consideraciones y Mejoras Posibles

1. **Manejo de Excepciones**: Implementar un manejador global de excepciones para manejar casos como productos no encontrados.
2. **Seguridad**: Añadir autenticación y autorización.
3. **Paginación**: Implementar paginación para el listado de productos.
4. **Tests**: Agregar pruebas unitarias y de integración.
5. **Documentación API**: Integrar Swagger/OpenAPI para documentación automática.
