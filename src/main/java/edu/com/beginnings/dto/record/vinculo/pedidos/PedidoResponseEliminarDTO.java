package edu.com.beginnings.dto.record.vinculo.pedidos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record PedidoResponseEliminarDTO(
        Integer id,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fecha_eliminado
) {
    public PedidoResponseEliminarDTO(Integer id, LocalDate fecha_eliminado) {
        this(id ,fecha_eliminado.atTime(LocalTime.now()));
    }
}
