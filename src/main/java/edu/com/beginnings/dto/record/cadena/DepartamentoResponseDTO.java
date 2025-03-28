package edu.com.beginnings.dto.record.cadena;

public record DepartamentoResponseDTO(
        Integer id,
        String nombre,
        EmpresaDTO empresa
) {
}
