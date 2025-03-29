package edu.com.beginnings.service.red;

import edu.com.beginnings.dto.red.AlumnoConCursosResponseDTO;
import edu.com.beginnings.dto.red.AlumnoConCursosYTalleresResponseDTO;
import edu.com.beginnings.dto.red.AlumnoConTalleresResponseDTO;

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


    //listado alumnos con talleres
    List<AlumnoConTalleresResponseDTO> listarAlumnosConTalleres();

    //listado curso-talleres
    List<AlumnoConCursosYTalleresResponseDTO> listadoCursoYTalleres();

}
