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
