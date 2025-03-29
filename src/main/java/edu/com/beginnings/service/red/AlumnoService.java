package edu.com.beginnings.service.red;

import edu.com.beginnings.dto.red.*;

import java.util.List;

public interface AlumnoService {



    //listado curso-talleres
    List<AlumnoResponseDTO> listarAlumnos();

    //request alumno
    AlumnoResponseDTO registrar(AlumnoRequestDTO alumnoRequestDTO);


}
