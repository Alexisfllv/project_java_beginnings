package edu.com.beginnings.dto.record.vinculo.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponseDTO(
        Integer id,
        LocalDate fecha,
        BigDecimal total,
        String estado,
        ClienteDTO cliente,
        SuministroDTO suministro
) {
}
