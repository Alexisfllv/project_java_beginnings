package edu.com.beginnings.map.red;


import edu.com.beginnings.dto.red.*;
import edu.com.beginnings.model.red.Alumno;
import edu.com.beginnings.model.red.AlumnoCurso;
import edu.com.beginnings.model.red.Curso;
import edu.com.beginnings.model.red.Taller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    //
    // Convertir Alumno a DTO de respuesta
    @Mapping(target = "cursos", source = "alumnoCursos", qualifiedByName = "mapCursos")
    @Mapping(target = "talleres", source = "talleres")
    AlumnoResponseDTO toAlumnoResponseDTO(Alumno alumno);



    // Convertir lista de AlumnoCurso a lista de CursoDTO
    @Named("mapCursos")
    default List<CursoDTO> mapCursos(List<AlumnoCurso> alumnoCursos) {
        if (alumnoCursos == null) return List.of();
        return alumnoCursos.stream()
                .map(ac -> new CursoDTO(ac.getCurso().getId(), ac.getCurso().getNombre()))
                .toList();
    }
}
