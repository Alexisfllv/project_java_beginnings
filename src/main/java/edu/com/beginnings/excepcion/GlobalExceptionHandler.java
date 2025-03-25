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
