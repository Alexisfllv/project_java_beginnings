package edu.com.beginnings.dto.record.base;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRequestRecordDTO(

        Integer id,

        @NotBlank
        @Size(max = 60)
        String nombre,

        @NotNull
        @Min(1)
        Integer cantidad,

        @NotNull
        @DecimalMin(value = "0.001", inclusive = true, message = "\uD83D\uDEA8 \uD83D\uDEA8 \uD83D\uDEA8 peso mayor a 0.001")
        BigDecimal peso



) {
}
