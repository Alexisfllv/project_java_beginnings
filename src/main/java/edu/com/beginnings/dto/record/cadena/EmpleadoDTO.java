package edu.com.beginnings.dto.record.cadena;

public record EmpleadoDTO(
        Integer id,
        String nombre,
        DepartamentoDTO departamento
) {
}
