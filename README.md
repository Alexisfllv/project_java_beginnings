
``` bash
src/main/java/com/tuempresa/proyecto
│── MainApplication.java  # Clase principal de Spring Boot
│── config/           # Configuraciones (CORS, seguridad, etc.)
│── controller/       # Controladores (APIs REST)
│── dto/              # Clases DTO (Data Transfer Object)
│── entity/           # Entidades JPA (Modelos de base de datos)
│── repository/       # Repositorios JPA (Interacción con BD)
│── service/          # Lógica de negocio (Servicios)
│── service/impl/     # Implementaciones de los servicios
│── exception/        # Manejo de excepciones personalizadas
│── util/             # Clases utilitarias (helpers)
│── security/         # Configuración de seguridad (JWT, autenticación)
│── mapper/           # Mapstruct u otros mapeadores de DTOs
│── enums/            # Enumeradores (si es necesario)
│── response/         # Clases para respuestas estándar
│── pagination/       # Clases para manejar paginación
```


### ENTITY

``` bash
src/main/java/com/corporation/proyect
│── entity/           # Entidades JPA (Modelos de base de datos)
```


``` java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;

    @Column(name = "item_name", nullable = false, length = 100)
    private String name;  // VARCHAR(100) en BD

    @Column(name = "item_quantity", nullable = false)
    private Integer quantity;  // INT en BD

    @Column(name = "item_active", nullable = false)
    private Boolean active;  // BOOLEAN en BD

    @Column(name = "item_created", nullable = false)
    private LocalDateTime created;  // DATETIME en BD

    @Column(name = "item_uuid", nullable = true, unique = true)
    private UUID uuid = UUID.randomUUID();   // UUID en BD
}
```
### DTO
``` bash
src/main/java/com/corporation/proyect
│── dto/              # Clases DTO (Data Transfer Object)
```

``` java
public record ItemRequestDTO(

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    @Schema(description = "Nombre del ítem", example = "Item ejemplo", required = true, maxLength = 100)
    String name,

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity should be greater than or equal to 0")
    @Schema(description = "Cantidad del ítem", example = "10", required = true, minimum = "0")
    Integer quantity,

    @NotNull(message = "Active status is required")
    @Schema(description = "Estado activo del ítem", example = "true", required = true)
    Boolean active

) {}

```
``` java
public record ItemResponseDTO(
    @Schema(description = "ID del ítem", example = "1", required = true)
    Integer id,

    @Schema(description = "Nombre del ítem", example = "Item ejemplo", required = true)
    String name,

    @Schema(description = "Cantidad del ítem", example = "10", required = true)
    Integer quantity,

    @Schema(description = "Estado activo del ítem", example = "true", required = true)
    Boolean active,

    @Schema(description = "Fecha de creación del ítem", example = "2023-04-02T15:30:00", required = true)
    LocalDateTime created,

    @Schema(description = "UUID del ítem", example = "dcb41d8f-c467-4f89-a90f-9e687db3edc3", required = true)
    UUID uuid
) {}
```

### MAPPER
``` bash
src/main/java/com/corporation/proyect
│── mapper/           # Mapstruct u otros mapeadores de DTOs
```

``` java
@Mapper(componentModel = "spring")
public interface IItemMapper {

    ItemResponseDTO toItemResponseDTO(Item item);
    Item toItem(ItemResponseDTO itemResponseDTO);

    ItemRequestDTO toItemRequestDTO(Item item);
    Item toItem(ItemRequestDTO itemRequestDTO);
}
```
### REPOSITORY
``` bash
src/main/java/com/corporation/proyect
│── repository/       # Repositorios JPA (Interacción con BD)
```
``` java
@Repository
public interface IItemRepository extends JpaRepository<Item, Integer> {
}
```
### EXCEPTION
``` bash
src/main/java/com/corporation/proyect
│── exception/        # Manejo de excepciones personalizadas
```
``` java
public class ExAccesoNoAutorizadoException extends RuntimeException {
    // 401 Unauthorized 403 Forbidden
    public ExAccesoNoAutorizadoException(String message) {
        super(message);
    }
}
```
``` java
public class ExDatabaseErrorException extends RuntimeException {
    public ExDatabaseErrorException(String message) {
        super(message);
    }
}
```
``` java
public class ExDataNotFoundException extends RuntimeException {
    public ExDataNotFoundException(String message) {
        super(message);
    }
}
```
``` java
public class ExIncorrectJsonException extends RuntimeException {
    public ExIncorrectJsonException(String message) {
        super(message);
    }
}
```
``` java
public class ExInvalidDataException extends RuntimeException {
    //400 Bad Request
    public ExInvalidDataException(String message) {
        super(message);
    }
}
```

``` java
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Constantes para keys de respuesta
    private static final String MESSAGE = "message";
    private static final String CODE = "code";
    private static final String ERRORS = "errors";

    // ---- Manejo de excepciones personalizadas ----
    @ExceptionHandler(ExAccesoNoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> handleAccesoNoAutorizado(ExAccesoNoAutorizadoException ex) {
        HttpStatus status = ex.getMessage().contains("Forbidden") ? HttpStatus.FORBIDDEN : HttpStatus.UNAUTHORIZED;
        return buildResponse(ex.getMessage(), status);
    }

    @ExceptionHandler(ExDataNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDataNotFound(ExDataNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExInvalidDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidData(ExInvalidDataException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExIncorrectJsonException.class)
    public ResponseEntity<Map<String, Object>> handleIncorrectJson(ExIncorrectJsonException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExDatabaseErrorException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseError(ExDatabaseErrorException ex) {
        return buildResponse("Database error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ---- Manejo de excepciones de Spring ----
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJson(HttpMessageNotReadableException ex) {
        return buildResponse("Invalid JSON format", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(),
                        fieldError -> fieldError.getDefaultMessage() != null ?
                                fieldError.getDefaultMessage() : "Invalid value"
                ));

        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, "Validation error");
        response.put(CODE, HttpStatus.BAD_REQUEST.value());
        response.put(ERRORS, errors);
        return ResponseEntity.badRequest().body(response);
    }

    // ---- Último recurso (Error genérico) ----
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Metodo helper para respuestas consistentes
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, message);
        response.put(CODE, status.value());
        return ResponseEntity.status(status).body(response);
    }
}
```

### RESPONSE
``` bash
src/main/java/com/corporation/proyect
│── response/         # Clases para respuestas estándar
```
``` java
public record ResponseDTO(
        String mensaje,
        Object data) {
}
```
``` java
@Getter
public enum ResponseMessage {
    SUCCESSFUL_ADDITION("Added successfully"),
    SUCCESSFUL_MODIFICATION("Modification completed successfully"),
    SUCCESSFUL_DELETION("Deletion completed successfully");
    
    private final String message;
    // Private constructor
    ResponseMessage(String message) {
        this.message = message;
    }
}
```



### PAGINATION
``` bash
src/main/java/com/corporation/proyect
│── pagination/       # Clases para manejar paginación
```

``` java
public record PageResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean isLast,
        boolean isFirst,
        int numberOfElements
) {
    public PageResponseDTO(Page<T> page) {
        this(page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst(),
                page.getNumberOfElements());
    }
}
```

### SERVICE
``` bash
src/main/java/com/corporation/proyect
│── service/          # Lógica de negocio (Servicios)
```

``` java
public interface IItemService {

    // List all
    List<ItemResponseDTO> getAllItems();

    // List all Pageable
    PageResponseDTO<ItemResponseDTO> getAllItemsPageable(Pageable pageable);

    // Get by ID
    ItemResponseDTO getItemById(Integer id);

    // Create
    ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO);

    // Create Response
    ResponseDTO createItemResponse(ItemRequestDTO itemRequestDTO);

    // Update
    ItemResponseDTO updateItem(ItemRequestDTO itemRequestDTO, Integer id);

    // Update Response
    ResponseDTO updateItemResponse(ItemRequestDTO itemRequestDTO, Integer id);

    // Delete
    void deleteItem(Integer id);

    // Delete Response
    ResponseDTO deleteItemResponse(Integer id);
}
```
### SERVICE/IMPL
``` bash
src/main/java/com/corporation/proyect
│── service/impl/     # Implementaciones de los servicios
```
``` java
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {

    private final IItemRepository itemRepository;
    private final IItemMapper itemMapper;

    @Override
    public List<ItemResponseDTO> getAllItems() {
        log.info("Obteniendo todos los items");
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemMapper::toItemResponseDTO)
                .toList();
    }

    @Override
    public PageResponseDTO<ItemResponseDTO> getAllItemsPageable(Pageable pageable) {
        log.info("Obteniendo items con paginación: {}", pageable);
        Page<ItemResponseDTO> paged = itemRepository.findAll(pageable)
                .map(itemMapper::toItemResponseDTO);
        return new PageResponseDTO<>(paged);
    }

    @Override
    public ItemResponseDTO getItemById(Integer id) {
        log.info("Buscando item con ID: {}", id);
        return itemMapper.toItemResponseDTO(findItemById(id));
    }

    @Override
    public ItemResponseDTO createItem(ItemRequestDTO itemRequestDTO) {
        log.info("Creando nuevo item: {}", itemRequestDTO);
        Item item = itemMapper.toItem(itemRequestDTO);
        item.setCreated(LocalDateTime.now());
        itemRepository.save(item);
        log.info("Item creado con éxito: {}", item);
        return itemMapper.toItemResponseDTO(item);
    }

    @Override
    public ResponseDTO createItemResponse(ItemRequestDTO itemRequestDTO) {
        log.info("Creando nuevo item con respuesta estructurada: {}", itemRequestDTO);
        ItemResponseDTO responseDTO = createItem(itemRequestDTO);
        return new ResponseDTO(ResponseMessage.SUCCESSFUL_ADDITION.getMessage(), responseDTO);
    }

    @Override
    public ItemResponseDTO updateItem(ItemRequestDTO itemRequestDTO, Integer id) {
        log.info("Actualizando item con ID: {}", id);
        Item itemRecovered = findItemById(id);
        itemRecovered.setName(itemRequestDTO.name());
        itemRecovered.setQuantity(itemRequestDTO.quantity());
        itemRecovered.setActive(itemRequestDTO.active());

        itemRepository.save(itemRecovered);
        log.info("Item actualizado con éxito: {}", itemRecovered);
        return itemMapper.toItemResponseDTO(itemRecovered);
    }

    @Override
    public ResponseDTO updateItemResponse(ItemRequestDTO itemRequestDTO, Integer id) {
        log.info("Actualizando item con respuesta estructurada, ID: {}", id);
        ItemResponseDTO responseDTO = updateItem(itemRequestDTO, id);
        return new ResponseDTO(ResponseMessage.SUCCESSFUL_MODIFICATION.getMessage(), responseDTO);
    }

    @Override
    public void deleteItem(Integer id) {
        log.warn("Eliminando item con ID: {}", id);
        findItemById(id);
        itemRepository.deleteById(id);
        log.info("Item eliminado con éxito, ID: {}", id);
    }

    @Override
    public ResponseDTO deleteItemResponse(Integer id) {
        log.warn("Eliminando item con respuesta estructurada, ID: {}", id);
        deleteItem(id);
        return new ResponseDTO(ResponseMessage.SUCCESSFUL_DELETION.getMessage(), "Item eliminado con ID: " + id);
    }

    private Item findItemById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Item no encontrado con ID: {}", id);
                    return new ExDataNotFoundException("ID item not found: " + id);
                });
    }
}
```
### CONTROLLER
``` bash
src/main/java/com/corporation/proyect
│── controller/       # Controladores (APIs REST)
```
``` java
@Tag(name = "Items", description = "API para gestionar Items")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @Operation(summary = "Listar todos los items")
    @ApiResponse(responseCode = "200", description = "Lista de items obtenida con éxito")
    @GetMapping("/all")
    public ResponseEntity<List<ItemResponseDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @Operation(summary = "Listar items con paginación")
    @ApiResponse(responseCode = "200", description = "Items listados correctamente")
    @GetMapping("/page")
    public ResponseEntity<PageResponseDTO<ItemResponseDTO>> pageItems(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(itemService.getAllItemsPageable(pageable));
    }

    @Operation(summary = "Obtener un item por ID")
    @ApiResponse(responseCode = "200", description = "Item encontrado")
    @ApiResponse(responseCode = "404", description = "Item no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> getItemById(@PathVariable Integer id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @Operation(summary = "Crear un nuevo item")
    @ApiResponse(responseCode = "201", description = "Item creado exitosamente")
    @PostMapping
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(201).body(itemService.createItem(itemRequestDTO));
    }

    @Operation(summary = "Crear un nuevo item con respuesta personalizada")
    @ApiResponse(responseCode = "201", description = "Item creado correctamente con respuesta personalizada")
    @PostMapping("/response")
    public ResponseEntity<ResponseDTO> createItemResponse(@RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.status(201).body(itemService.createItemResponse(itemRequestDTO));
    }

    @Operation(summary = "Actualizar un item existente")
    @ApiResponse(responseCode = "200", description = "Item actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Item no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Integer id, @RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.ok(itemService.updateItem(itemRequestDTO, id));
    }

    @Operation(summary = "Actualizar un item con respuesta personalizada")
    @ApiResponse(responseCode = "200", description = "Item actualizado con éxito")
    @PutMapping("/response/{id}")
    public ResponseEntity<ResponseDTO> updateItemResponse(
            @PathVariable Integer id, @RequestBody ItemRequestDTO itemRequestDTO) {
        return ResponseEntity.ok(itemService.updateItemResponse(itemRequestDTO, id));
    }

    @Operation(summary = "Eliminar un item por ID")
    @ApiResponse(responseCode = "200", description = "Item eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Item no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Eliminar un item con respuesta personalizada")
    @ApiResponse(responseCode = "200", description = "Item eliminado con éxito y detalles adicionales enviados")
    @DeleteMapping("/response/{id}")
    public ResponseEntity<ResponseDTO> deleteItemResponse(@PathVariable Integer id) {
        return ResponseEntity.ok(itemService.deleteItemResponse(id));
    }
}
```
