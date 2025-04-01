package edu.com.beginnings.map.red;


import edu.com.beginnings.dto.record.red.AlumnoGlobalRequestDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalResponseDTO;
import edu.com.beginnings.model.red.Alumno;
import edu.com.beginnings.model.red.AlumnoCurso;
import edu.com.beginnings.model.red.Curso;
import edu.com.beginnings.model.red.Taller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    //request
    Alumno toAlumno(AlumnoGlobalRequestDTO alumnoGlobalRequestDTO);

    //response
    @Mapping(target = "cursos",source = "alumnoCursos",qualifiedByName = "mapCursos")
    @Mapping(target = "talleres",source = "talleres",qualifiedByName = "mapTalleres")
    AlumnoGlobalResponseDTO toAlumnoGlobalResponseDTO(Alumno alumno);

    @Named("mapCursos")
    default List<String> mapCursos(List<AlumnoCurso> alumnoCursos){
        return alumnoCursos
                .stream()
                .map(ac -> ac.getCurso().getNombre())
                .collect(Collectors.toList());
    }

    @Named("mapTalleres")
    default List<String> mapTalleres(List<Taller> talleres){
        return talleres
                .stream()
                .map(taller -> taller.getNombre())
                .collect(Collectors.toList());
    }

}
