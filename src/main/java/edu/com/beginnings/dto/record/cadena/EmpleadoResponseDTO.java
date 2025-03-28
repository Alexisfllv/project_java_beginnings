package edu.com.beginnings.dto.record.cadena;

public record EmpleadoResponseDTO(
        Integer id,
        String nombre,
        DepartamentoDTO departamento
) {
}
