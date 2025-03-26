package edu.com.beginnings.dto.record.vinculo.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClienteDTO(
        Integer id,
        String nombre,
        String email,
        Integer telefono

) {


}
