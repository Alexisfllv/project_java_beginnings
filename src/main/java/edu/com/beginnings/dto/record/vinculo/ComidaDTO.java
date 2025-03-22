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
