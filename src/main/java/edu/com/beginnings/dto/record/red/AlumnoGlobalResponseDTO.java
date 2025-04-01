package edu.com.beginnings.dto.record.red;

import java.util.List;

public record AlumnoGlobalResponseDTO(
        Integer id,
        String nombre,
        List<String> cursos,
        List<String> talleres
) {
}
