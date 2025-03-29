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

    // Convertir IDs de cursos a entidades AlumnoCurso (sin asignar alumno aún)
//    @Named("mapAlumnoCursos")
//    default List<AlumnoCurso> mapAlumnoCursos(List<Integer> cursoIds) {
//        if (cursoIds == null) return List.of();
//        return cursoIds.stream()
//                .map(id -> new AlumnoCurso(null, null, new Curso(id, null), "ACTIVO"))
//                .toList();
//    }
//
//    // Convertir IDs de talleres a entidades Taller
//    @Named("mapTalleres")
//    default List<Taller> mapTalleres(List<Integer> tallerIds) {
//        if (tallerIds == null) return List.of();
//        return tallerIds.stream()
//                .map(id -> new Taller(id, null))
//                .toList()
// }

    // Convertir lista de AlumnoCurso a lista de CursoDTO
    @Named("mapCursos")
    default List<CursoDTO> mapCursos(List<AlumnoCurso> alumnoCursos) {
        if (alumnoCursos == null) return List.of();
        return alumnoCursos.stream()
                .map(ac -> new CursoDTO(ac.getCurso().getId(), ac.getCurso().getNombre()))
                .toList();
    }
}
