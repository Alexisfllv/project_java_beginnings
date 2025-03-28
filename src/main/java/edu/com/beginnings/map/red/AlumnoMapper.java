package edu.com.beginnings.map.red;


import edu.com.beginnings.dto.red.AlumnoConCursosResponseDTO;
import edu.com.beginnings.dto.red.AlumnoResponseDTO;
import edu.com.beginnings.dto.red.CursoDTO;
import edu.com.beginnings.model.red.Alumno;
import edu.com.beginnings.model.red.AlumnoCurso;
import edu.com.beginnings.model.red.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    //Mapeo a response basico
    AlumnoResponseDTO toAlumnoResponseDTO(Alumno alumno);
    Alumno toAlumno(AlumnoResponseDTO alumnoResponseDTO);

    //
    CursoDTO toCursoDTO(Curso curso);
    Curso toCurso(CursoDTO cursoDTO);


    //mapeo para listado de alumnos con cursos
    @Mapping(target = "cursos" , source = "alumnoCursos" , qualifiedByName = "mapCursos")
    AlumnoConCursosResponseDTO toAlumnoConCursosResponseDTO(Alumno alumno);

    @Named("mapCursos")
    default List<CursoDTO> mapCursos(List<AlumnoCurso> alumnoCursos){
        return alumnoCursos.stream()
                .map(ac -> new CursoDTO(ac.getCurso().getId(),ac.getCurso().getNombre()))
                .toList();
    }

}
