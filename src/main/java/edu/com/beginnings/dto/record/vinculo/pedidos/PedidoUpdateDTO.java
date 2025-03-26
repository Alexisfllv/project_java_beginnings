package edu.com.beginnings.dto.record.vinculo.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoUpdateDTO(

        LocalDate fecha,
        BigDecimal total,
        String estado,
        Integer clienteId,
        Integer suministroId
) {
}
