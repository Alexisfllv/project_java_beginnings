package edu.com.beginnings.service.red;

import edu.com.beginnings.dto.red.AlumnoConCursosResponseDTO;

import java.util.List;

public interface AlumnoService {

    //listado de alumnos
    List<AlumnoConCursosResponseDTO> listarAlumnosConCursos();
    //buscar alumnos
    AlumnoConCursosResponseDTO buscarAlumnoConCurso(Integer id);


    //DATOS JPQL
    List<AlumnoConCursosResponseDTO> findAlumnosConCursosPorCurso();

    //NATIE QUERY
    List<AlumnoConCursosResponseDTO> findAlumnosConCursosPorCursoNative();

}
