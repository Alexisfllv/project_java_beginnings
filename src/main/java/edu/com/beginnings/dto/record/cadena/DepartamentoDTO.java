package edu.com.beginnings.dto.record.cadena;

public record DepartamentoDTO(
        Integer id,
        String nombre,
        EmpresaDTO empresa
) {
}
