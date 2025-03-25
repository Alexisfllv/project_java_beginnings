necesito que documentes este servicio pero antes me digas que tipo de servicio es , que usa , que patrones de diseno aplica ,
al final menciona todo lo que conlleva y una calificion como servicio todo en archivo .md para un repositorio github. 
package edu.com.beginnings.model.vinculo;

modelos : 
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name = "categoria_nombre")
    private String nombre;
}
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comidas")
public class Comida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comida_id")
    private Integer id;

    @Column(name = "comida_nombre",nullable = false)
    private String nombre;

    @Column(name = "producto_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "producto_peso", precision = 10, scale = 3, nullable = false)
    private BigDecimal peso;

    @Column(name = "producto_fecha_inicio", nullable = true)
    private LocalDateTime fechaInicio;

    @Column(name = "producto_fecha_fin", nullable = true)
    private LocalDateTime fechaFin;

    //fk
    @ManyToOne                //defecto
    @JoinColumn(name = "categoria_id", nullable = false) // No permite NULL
    private Categoria categoria;

}

dtos para manejar mejor los servicios , 
package edu.com.beginnings.dto.record.vinculo;

public record CategoriaDTO(
        Integer id,
        String nombre
) {
}
package edu.com.beginnings.dto.record.vinculo;

public record CategoriaRequestDTO(
        Integer id,
        String nombre
) {
}
package edu.com.beginnings.dto.record.vinculo;

public record CategoriaResponseDTO(
        Integer id,
        String nombre
) {
}
package edu.com.beginnings.dto.record.vinculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComidaDTO(
        Integer id,
        String nombre,
        Integer cantidad,
        BigDecimal peso,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
package edu.com.beginnings.dto.record.vinculo;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ComidaRequestDTO(

        @NotBlank(message = "El nombre de comida no puede estar vacío")
        @Pattern(regexp = "^[A-Za-z ]+$", message = "Solo se permiten letras y espacios")
        @Size(min = 1, max = 50, message = "El nombre de comida debe tener entre 1 y 50 caracteres")
        String nombre,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad,

        @NotNull(message = "El peso no puede ser nulo")
        @Digits(integer = 7, fraction = 3, message = "El peso debe tener hasta 7 dígitos enteros y 3 decimales")
        @DecimalMin(value = "0.001", message = "El peso debe ser mayor a 0.001")
        BigDecimal peso,

        @NotNull(message = "El ID de la categoría no puede ser nulo")
        @Positive(message = "El ID de la categoría debe ser un número positivo")
        Integer categoriaId

) {
}
package edu.com.beginnings.dto.record.vinculo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComidaResponseDTO(
        Integer id,
        String nombre,
        Integer cantidad,
        BigDecimal peso,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,

        //vista de la cateogria dto
        CategoriaDTO categoria
) {
}
package edu.com.beginnings.dto.record.vinculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComidaUpdateDTO(
        //
        String nombre,
        Integer cantidad,
        BigDecimal peso,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer categoriaId
) {}

package edu.com.beginnings.dto.record.vinculo.busquedas;

import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComidaResponsePesoDTO(
        Integer id,
        String nombre,
        Integer cantidad,
        BigDecimal peso,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,

        //vista de la cateogria dto
        CategoriaDTO categoria
) {
}
 , tambien el mapperstruct
 package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    // MODEL DTO
    CategoriaDTO categoriaDTO(Categoria categoria);
    Categoria categoria(CategoriaDTO categoriaDTO);

    //req
    CategoriaRequestDTO categoriaRequestDTO(CategoriaRequestDTO categoriaRequestDTO);
    Categoria categoriaReq(CategoriaRequestDTO categoriaRequestDTO);

    //res
    CategoriaResponseDTO categoriaResponseDTO(Categoria categoria);
    Categoria categoriaRes(CategoriaResponseDTO categoriaResponseDTO);
}

package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.model.vinculo.Comida;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComidaMapper {

    // DTO <- Entidad
    ComidaDTO comidaDTO(Comida comida);
    Comida comida(ComidaDTO comidaDTO);

    //response
    ComidaResponseDTO comidaResponseDTO(Comida comida);
    Comida comidaRes(ComidaResponseDTO comidaResponseDTO);

    //request
    ComidaRequestDTO comidaRequestDTO(Comida comida);

    @Mapping(target = "categoria.id" , source = "categoriaId")
    Comida comidaReq(ComidaRequestDTO comidaRequestDTO);

    //mapper para listado de busqueda

    ComidaResponsePesoDTO comidaResponsePesoDTO(Comida comida);

}
 , tambien para mis mensajes de repuesta por POST,PUT,DELETE
 package edu.com.beginnings.mensaje;

import lombok.Getter;

@Getter
public enum MensajeRespuesta {
    MODIFICACION_EXITOSA("Modificación realizada con éxito"),
    ELIMINACION_EXITOSA("Eliminación realizada con éxito"),
    AGREGADO_EXITOSO("Agregado correctamente");

    private final String mensaje;

    // Constructor privado
    MensajeRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }
}
package edu.com.beginnings.mensaje;

public record RespuestaDTO(String mensaje, Object data) {
}
 , tambien mi dto para paginar listas o busquedas de gran volumen :
 package edu.com.beginnings.paginador;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaRespuestaDTO<T>(
        List<T> contenido,
        int pagina,
        int tamano,
        long totalElementos,
        int totalPaginas,
        boolean ultima,
        boolean primera,
        int numeroElementos
) {
    public PaginaRespuestaDTO(Page<T> page) {
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
, y tambien la captura de excepciones

, package edu.com.beginnings.excepcion.errores;

public class AccesoNoAutorizadoException extends RuntimeException {


    
    public AccesoNoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}

,package edu.com.beginnings.excepcion.errores;

public class DatosInvalidosException extends RuntimeException {


    
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
,
package edu.com.beginnings.excepcion.errores;

public class ErrorBaseDatosException extends RuntimeException {

    public ErrorBaseDatosException(String message) {
        super(message);
    }
}
,
package edu.com.beginnings.excepcion.errores;

public class JsonMalFormadoException extends RuntimeException {

    public JsonMalFormadoException(String mensaje) {
        super(mensaje);
    }
}
,
package edu.com.beginnings.excepcion.errores;

public class RecursoNoEncontradoException  extends RuntimeException {


    //constructor 404
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

,
package edu.com.beginnings.excepcion;

import edu.com.beginnings.excepcion.errores.DatosInvalidosException;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //JSON MAL ESCRITIO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJson(HttpMessageNotReadableException ex) {
        Map<String, Object> response = Map.of(
                "mensaje", "El JSON enviado es inválido o está mal formado.",
                "detalle", ex.getMostSpecificCause().getMessage(),
                "codigo", HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    // ERROR 404 - Recurso no encontrado
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String , Object>> handleNotFound(RecursoNoEncontradoException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        response.put("codigo", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ERROR 400 - Datos inválidos por regla de negocio
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String , Object>> handleInvalid(DatosInvalidosException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        response.put("codigo", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ERROR 400 - Validaciones con @Valid al DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();

        // Lista de errores detallados con los campos y mensajes de error
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        response.put("mensaje", "Error de validación. Revisa los datos enviados.");
        response.put("codigo", HttpStatus.BAD_REQUEST.value());
        response.put("errores", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ERROR 400 - Errores de base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseErrors(DataIntegrityViolationException ex) {
        String mensajeError = "Error de integridad en la base de datos.";

        if (ex.getMessage().contains("foreign key constraint fails")) {
            mensajeError = "No se puede eliminar el registro porque tiene datos relacionados.";
        } else if (ex.getMessage().contains("Duplicate entry")) {
            mensajeError = "El registro ya existe en la base de datos.";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", mensajeError);
        response.put("codigo", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // ERROR 500 - Excepción general (debe ir al final)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Error interno del servidor.");
        response.put("detalle", ex.getMessage());
        response.put("codigo", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}


 ,luego mis repositorios ,
 package edu.com.beginnings.repo.vinculo;

import edu.com.beginnings.model.vinculo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepo extends JpaRepository<Categoria, Integer> {
}
package edu.com.beginnings.repo.vinculo;


import edu.com.beginnings.model.vinculo.Comida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ComidaRepo extends JpaRepository<Comida, Integer> {


    @Query("SELECT c FROM Comida  c where c.peso > :peso")
    Page<Comida> buscarComidasPesoMayorPesoPaginado(@Param("peso") BigDecimal peso , Pageable pageable);

}
 , 

 luego mis servicos
 package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;

import java.util.List;

public interface CategoriaService {

    //listar
    List<CategoriaResponseDTO> listados();

    //buscar
    CategoriaResponseDTO buscar(Integer id);

    //agregar
    CategoriaResponseDTO registrar(CategoriaRequestDTO categoriaRequestDTO);

    //modificar
    CategoriaResponseDTO modificar(CategoriaDTO categoriaDTO,Integer id);

    //eliminar
    void eliminar(Integer id);


}
 , package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ComidaService {

    //listar Comidas con detalles
    List<ComidaResponseDTO> listarComidas();

    //listar Con paginador //poli
    PaginaRespuestaDTO<ComidaResponseDTO> listarComidasp(Pageable pageable);


    //buscar por id de comidas
    ComidaResponseDTO buscarComida(Integer id);

    //metodo de registrar comida
    RespuestaDTO registrarComida(ComidaRequestDTO comidaRequestDTO);

    //modificar comida
    RespuestaDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO, Integer id);
    //ComidaResponseDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO , Integer id);

    //eliminar comiad
    RespuestaDTO eliminarComida(Integer id);

    //filtros jpql
    PaginaRespuestaDTO<ComidaResponsePesoDTO> buscarComidasPesoMayorPesoPaginado(BigDecimal peso ,Pageable pageable);




}

luego la implementacion de los servicios

package edu.com.beginnings.serviceImpl.vinculo;

import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.map.vinculo.CategoriaMapper;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.repo.vinculo.CategoriaRepo;
import edu.com.beginnings.service.vinculo.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    //ioc
    private final CategoriaRepo categoriaRepo;

    private final CategoriaMapper categoriaMapper;


    @Override
    public List<CategoriaResponseDTO> listados() {
        List<Categoria> categorias = categoriaRepo.findAll();
        return categorias.stream()
                .map(categoria -> categoriaMapper.categoriaResponseDTO(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO buscar(Integer id) {
        Categoria categoria = categoriaRepo.findById(id).get();
        return categoriaMapper.categoriaResponseDTO(categoria);

    }

    @Override
    public CategoriaResponseDTO registrar(CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = categoriaMapper.categoriaReq(categoriaRequestDTO);


        categoria = categoriaRepo.save(categoria);

        return categoriaMapper.categoriaResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO modificar(CategoriaDTO categoriaDTO,Integer id) {
        Categoria categiriaexiste = categoriaRepo.findById(id).get();

        //atributos
        if (categoriaDTO.nombre() != null ) {
            categiriaexiste.setNombre(categoriaDTO.nombre());
        }



        categiriaexiste = categoriaRepo.save(categiriaexiste);
        return categoriaMapper.categoriaResponseDTO(categiriaexiste);
    }

    @Override
    public void eliminar(Integer id) {
        categoriaRepo.deleteById(id);
    }
}
package edu.com.beginnings.serviceImpl.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.excepcion.errores.DatosInvalidosException;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.vinculo.ComidaMapper;
import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.vinculo.CategoriaRepo;
import edu.com.beginnings.repo.vinculo.ComidaRepo;
import edu.com.beginnings.service.vinculo.ComidaService;
import edu.com.beginnings.mensaje.RespuestaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComidaServiceImpl implements ComidaService {

    // repo
    private final ComidaRepo comidaRepo;
    private final CategoriaRepo categoriaRepo;

    //mapper
    private final ComidaMapper comidaMapper;


    //listar comidas con detalles
    @Override
    public List<ComidaResponseDTO> listarComidas() {
        List<Comida> comidas = comidaRepo.findAll();
        return comidas.stream()
                .map(comida -> comidaMapper.comidaResponseDTO(comida))
                .collect(Collectors.toList());

    }

    //listar page / poli
    @Override
    public PaginaRespuestaDTO<ComidaResponseDTO> listarComidasp(Pageable pageable) {
        Page<ComidaResponseDTO> pagina = comidaRepo.findAll(pageable)
                .map(comida -> comidaMapper.comidaResponseDTO(comida));

        return new PaginaRespuestaDTO<>(pagina);
    }

    //buscar por id
    @Override
    public ComidaResponseDTO buscarComida(Integer id) {
        Comida comida = comidaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comida no encontrada: " + id));

        return comidaMapper.comidaResponseDTO(comida);
    }


    //registrar
    @Override
    public RespuestaDTO registrarComida(ComidaRequestDTO comidaRequestDTO) {

        //bucar la id para ingresar
        Categoria categoria = categoriaRepo.findById(comidaRequestDTO.categoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Id Categoria no encontrado : " + comidaRequestDTO.categoriaId()));

        Comida comida = comidaMapper.comidaReq(comidaRequestDTO);
        //enviar la categoria registrada
        comida.setCategoria(categoria);

        //gestion de fechas
        if (comida.getFechaInicio() != null) {
            comida.setFechaInicio(comida.getFechaInicio());
        } else {
            comida.setFechaInicio(LocalDateTime.now());
        }

        if (comida.getFechaFin() != null) {
            comida.setFechaFin(comida.getFechaFin());
        } else {
            comida.setFechaFin(LocalDateTime.now().plusDays(3));
        }

        //guardar al modelo comida
        comida = comidaRepo.save(comida);

        //adaptar respuesta
        ComidaResponseDTO responseDTO = comidaMapper.comidaResponseDTO(comida);

        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(), responseDTO);
    }

    @Override
    public RespuestaDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO, Integer id) {

        // Buscar la comida en la BD
        Comida comida = comidaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comida no encontrada : " + id));

        //Actualizar Valores
        if (comidaUpdateDTO.nombre() != null) {
            comida.setNombre(comidaUpdateDTO.nombre());
        }
        if (comidaUpdateDTO.cantidad() != null) {
            comida.setCantidad(comidaUpdateDTO.cantidad());
        }
        if (comidaUpdateDTO.peso() != null) {
            comida.setPeso(comidaUpdateDTO.peso());
        }
        if (comidaUpdateDTO.fechaInicio() != null) {
            comida.setFechaInicio(comidaUpdateDTO.fechaInicio());
        }
        if (comidaUpdateDTO.fechaFin() != null) {
            comida.setFechaFin(comidaUpdateDTO.fechaFin());
        }
        //modificacion de la categoria
        if (comidaUpdateDTO.categoriaId() != null) {
            Categoria categoria = categoriaRepo.findById(comidaUpdateDTO.categoriaId())
                    .orElseThrow(() -> new RuntimeException("Id Categoria no encontrado"));

            comida.setCategoria(categoria);
        }
        //guardar los cambios
        comida = comidaRepo.save(comida);

        //Construir respuesta con mensaje
        ComidaResponseDTO dataDTO = comidaMapper.comidaResponseDTO(comida);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(), dataDTO);
    }

    @Override
    public RespuestaDTO eliminarComida(Integer id) {
        Comida comida = comidaRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comida no encontrada : " + id)); // Validar existencia
        //
        comidaRepo.delete(comida);
        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), id);
    }


    //metodo de busqueda por peso mayor

    @Override
    public PaginaRespuestaDTO<ComidaResponsePesoDTO> buscarComidasPesoMayorPesoPaginado(BigDecimal peso,Pageable pageable) {

        Page<Comida> pagina = comidaRepo.buscarComidasPesoMayorPesoPaginado(peso,pageable);

        // convertir cada comida en comidaResponsePesoDTO usando map
        Page<ComidaResponsePesoDTO> paginaDTO = pagina.map(comida -> comidaMapper.comidaResponsePesoDTO(comida));

        return new PaginaRespuestaDTO<>(paginaDTO);

    }






}
 , luego los controladores.
 package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.service.vinculo.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinculo/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    //ioc
    private final CategoriaService categoriaService;

    @GetMapping("/listar")
    public List<CategoriaResponseDTO> listar() {
        return ResponseEntity.ok(categoriaService.listados()).getBody();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscar(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<CategoriaResponseDTO> registrar(@RequestBody CategoriaRequestDTO CategoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.registrar(CategoriaRequestDTO));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaResponseDTO> modificar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaResponseDTO modificar = categoriaService.modificar(categoriaDTO,id);
        return ResponseEntity.ok(modificar);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
 , package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.vinculo.ComidaRepo;
import edu.com.beginnings.service.vinculo.ComidaService;
import edu.com.beginnings.mensaje.RespuestaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/vinculo/comidas")
@RequiredArgsConstructor
public class ComidaController {

    //ioc
    private final ComidaService comidaService;



    //listar comidas con detallas
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista completa de usuarios")
    @GetMapping("/listar")
    public ResponseEntity<List<ComidaResponseDTO>> listarComidas() {
        return ResponseEntity.status(200).body(comidaService.listarComidas());
    }

    @GetMapping("/listar-page")
    public ResponseEntity<PaginaRespuestaDTO<ComidaResponseDTO>> listarComidasPaginadas(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(comidaService.listarComidasp(pageable));
    }

    // Comida service




    //BUscar x id
    @GetMapping("/buscar/{id}")
    public ComidaResponseDTO buscarComida(@PathVariable Integer id) {
        ComidaResponseDTO comida = comidaService.buscarComida(id);
        return ResponseEntity.status(200).body(comida).getBody();
    }



    @PostMapping("/registrar")
    public ResponseEntity<RespuestaDTO> registrar(@Valid  @RequestBody ComidaRequestDTO comidaRequestDTO) {
        RespuestaDTO response_data = comidaService.registrarComida(comidaRequestDTO);
        return ResponseEntity.status(201).body(response_data);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<RespuestaDTO> modificar(@PathVariable Integer id, @Valid @RequestBody ComidaUpdateDTO comidaUpdateDTO) {

        RespuestaDTO response_data = comidaService.modificarComida(comidaUpdateDTO, id);
        return ResponseEntity.status(200).body(response_data);
    }

    //eliminar

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Integer id) {
        RespuestaDTO response_data = comidaService.eliminarComida(id);
        return ResponseEntity.status(200).body(response_data);
    }


    // busqueda jpql buscar por peso mayor
    @GetMapping("/buscar/mayorpeso/{peso}/listar-page")
    public ResponseEntity<PaginaRespuestaDTO<ComidaResponsePesoDTO>> buscarMayorPeso(@PathVariable BigDecimal peso,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(comidaService.buscarComidasPesoMayorPesoPaginado(peso,pageable));
    }



}
, pom xml : 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>edu.com</groupId>
    <artifactId>beginnings</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>beginnings</name>
    <description>beginnings</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>

    <properties>
        <java.version>21</java.version>
        <org.mapstruct.version>1.6.3</org.mapstruct.version>
    </properties>

    <dependencies>
        <!-- SPRING BOOT -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- MySQL -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <!-- MapStruct -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${org.mapstruct.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>${org.mapstruct.version}</version>
            <scope>provided</scope>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- mvc -->

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <!-- validaciones dtos , records -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- SWAGGER -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.3.0</version>
        </dependency>

        <!-- SPRING DOCS -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.8.6</version>
        </dependency>




    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <release>21</release>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>1.18.30</version>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${org.mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
 y el properties : 
 spring.application.name=beginnings

spring.datasource.url=jdbc:mysql://localhost:3306/beginning_java?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=deadmau5
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true



#Thymeleaf
spring.thymeleaf.encoding=UTF-8

springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html





