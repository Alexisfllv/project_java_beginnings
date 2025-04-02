
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
``` java
public record ItemUpdateDTO(
    
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
### MAPPER
``` bash
src/main/java/com/corporation/proyect
│── dto/              # Clases DTO (Data Transfer Object)
```

```