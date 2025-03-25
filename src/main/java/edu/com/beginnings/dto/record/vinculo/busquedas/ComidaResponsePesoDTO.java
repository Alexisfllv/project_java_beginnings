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
