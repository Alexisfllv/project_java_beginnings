package edu.com.beginnings.dto.record.vinculo.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidoRequestDTO(


        BigDecimal total,
        String estado,

        //ingreso
        Integer clienteId,

        //
        Integer suministroId
) {
}
