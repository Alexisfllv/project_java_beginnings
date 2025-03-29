package edu.com.beginnings.dto.red;

import java.util.List;

public record AlumnoConTalleresResponseDTO(
        Integer id,
        String nombre,
        List<TallerDTO> talleres
) {
}
