package edu.com.beginnings.dto.record.vinculo.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoResponseDTO(
        LocalDate fecha,
        BigDecimal total,
        String estado,
        ClienteDTO cliente,
        SuministroDTO suministro
) {
}
