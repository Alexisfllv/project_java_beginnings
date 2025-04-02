
``` bash
src/main/java/com/tuempresa/proyecto
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
│── MainApplication.java  # Clase principal de Spring Boot
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

    @Column(name = "item_uuid", nullable = false, unique = true)
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
    String name,

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity should be greater than or equal to 0") 
    Integer quantity,

    @NotNull(message = "Active status is required")
    Boolean active,

    @NotNull(message = "Creation date is required")
    LocalDateTime created
){}
```
``` java
public record ItemResponseDTO(
    Integer id,
    String name,
    Integer quantity,
    Boolean active,
    LocalDateTime created,
    UUID uuid
){}
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
public class AccesoNoAutorizadoException extends RuntimeException {
    // 401 Unauthorized 403 Forbidden
    public AccesoNoAutorizadoException(String message) {
        super(message);
    }
}
```






### SERVICE
``` bash
src/main/java/com/corporation/proyect
│── service/          # Lógica de negocio (Servicios)
```

``` java
public interface ItemService {

    // List all 
    List<ItemResponseDTO> getAllItems();

    // Get by ID
    ItemResponseDTO getItemById(Integer id);

    // Create 
    ItemResponseDTO createItem(WorkshopRequestDTO ItemRequestDTO);

    // Update
    ItemResponseDTO updateItem(ItemRequestDTO itemRequestDTO, Integer id);

    // Delete
    void deleteItem(Integer id);
}
```
### SERVICE/IMPL
``` bash
src/main/java/com/corporation/proyect
│── service/impl/     # Implementaciones de los servicios
```
``` java
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    // ioc
    // repository
    private final ItemRepository itemRepository;
    // mapper
    private final ItemMapper itemMapper;


    @Override
    public List<ItemResponseDTO> getAllItems() {
        List<Item> listItems = itemRepository.findAll();
        return listItems.stream()
                .map(item -> itemMapper.toItemResponseDTO(item))
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDTO getItemById(Integer id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("The item does not exist: " + id));

        return itemMapper.toItemResponseDTO(item);
    }

    @Override
    public ItemResponseDTO createItem(toItemRequestDTO itemRequestDTO) {
        Item item = itemMapper.toItem(itemRequestDTO);

        item.setName(item.getName());
        item.setQuantity(item.getQuantity)

        tallerRepository.save(taller);

        return tallerSimpleMapper.toTallerSimpleResponseDTO(taller);
    }

    @Override
    public TallerSimpleResponseDTO actualizarTaller(TallerSimpleUpdateDTO tallerSimpleUpdateDTO, Integer id) {
        Taller tallerExiste = tallerRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("El taller no existe: " + id));

        //enviar nombre de la update
        tallerExiste.setNombre(tallerSimpleUpdateDTO.nombre());
        tallerRepository.save(tallerExiste);

        return tallerSimpleMapper.toTallerSimpleResponseDTO(tallerExiste);
    }

    @Override
    public void eliminarTaller(Integer id) {
        Taller tallerExiste = tallerRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("El taller no existe: " + id));
        tallerRepository.delete(tallerExiste);
    }
}
```