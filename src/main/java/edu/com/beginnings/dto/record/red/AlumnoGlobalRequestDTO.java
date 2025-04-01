package edu.com.beginnings.dto.record.red;

import java.util.List;

public record AlumnoGlobalRequestDTO(
        String nombre,
        List<Integer> cursosIds,
        List<Integer> talleresIds
) {

}
