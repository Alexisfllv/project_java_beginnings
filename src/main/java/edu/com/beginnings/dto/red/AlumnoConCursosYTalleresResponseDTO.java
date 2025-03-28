package edu.com.beginnings.dto.red;

import java.util.List;

public record AlumnoConCursosYTalleresResponseDTO(
        Integer id,
        String nombre,
        List<CursoDTO> cursos,
        List<TallerDTO> talleres
) {}
