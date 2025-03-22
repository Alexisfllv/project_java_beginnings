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