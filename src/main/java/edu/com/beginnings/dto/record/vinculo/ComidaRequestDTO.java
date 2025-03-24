package edu.com.beginnings.dto.record.vinculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ComidaRequestDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull
        Integer cantidad,

        @NotNull
        BigDecimal peso,

        @NotNull
        Integer categoriaId

) {
}
