package edu.com.beginnings.dto.red;

import java.util.List;

public record AlumnoRequestDTO(
        String nombre,
        List<Integer> cursos,
        List<Integer> talleres
) {
}
