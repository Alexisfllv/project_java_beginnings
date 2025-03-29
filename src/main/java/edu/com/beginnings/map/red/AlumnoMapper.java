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

    //Mapeo a response basico
    AlumnoResponseDTO toAlumnoResponseDTO(Alumno alumno);
    Alumno toAlumno(AlumnoResponseDTO alumnoResponseDTO);

    // Conversión de Curso
    CursoDTO toCursoDTO(Curso curso);
    Curso toCurso(CursoDTO cursoDTO);

    // Conversión de Taller
    TallerDTO toTallerDTO(Taller taller);
    Taller toTaller(TallerDTO tallerDTO);

    // Mapeo para listado de alumnos con cursos
    @Mapping(target = "cursos", source = "alumnoCursos", qualifiedByName = "mapCursos")
    AlumnoConCursosResponseDTO toAlumnoConCursosResponseDTO(Alumno alumno);

    @Named("mapCursos")
    default List<CursoDTO> mapCursos(List<AlumnoCurso> alumnoCursos) {
        return alumnoCursos.stream()
                .map(ac -> new CursoDTO(ac.getCurso().getId(), ac.getCurso().getNombre()))
                .toList();
    }

    // Mapeo para listado de alumnos con talleres
    @Mapping(target = "talleres", source = "talleres", qualifiedByName = "mapTalleres")
    AlumnoConTalleresResponseDTO toAlumnoConTalleresResponseDTO(Alumno alumno);

    @Named("mapTalleres")
    default List<TallerDTO> mapTalleres(List<Taller> talleres) {
        return talleres.stream()
                .map(t -> new TallerDTO(t.getId(), t.getNombre()))
                .toList();
    }

    //
    // Mapeo para listado de alumnos con cursos y talleres
    @Mapping(target = "cursos", source = "alumnoCursos", qualifiedByName = "mapCursos")
    @Mapping(target = "talleres", source = "talleres")
    AlumnoConCursosYTalleresResponseDTO toAlumnoConCursosYTalleresResponseDTO(Alumno alumno);
}
