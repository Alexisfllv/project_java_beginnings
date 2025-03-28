package edu.com.beginnings.dto.red;

import java.util.List;

public record AlumnoConCursosResponseDTO(
        Integer id ,
        String nombre,
        List<CursoDTO> cursos
) {
}
