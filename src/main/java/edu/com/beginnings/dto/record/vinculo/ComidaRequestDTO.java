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
