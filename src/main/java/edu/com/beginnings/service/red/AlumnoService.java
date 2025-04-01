package edu.com.beginnings.service.red;



import edu.com.beginnings.dto.record.red.AlumnoGlobalRequestDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalResponseDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalUpdateDTO;

import java.util.List;

public interface AlumnoService {

    //listas
    List<AlumnoGlobalResponseDTO> listadoAlumnos();

    //buscar id
    AlumnoGlobalResponseDTO buscarAlumnoporid(Integer id);

    //registrado
    AlumnoGlobalResponseDTO crearAlumno(AlumnoGlobalRequestDTO alumnoGlobalRequestDTO);

    //modificar
    AlumnoGlobalResponseDTO modificarAlumno(Integer id, AlumnoGlobalUpdateDTO alumnoGlobalUpdateDTO);

    //eliminar

    void eliminarAlumno(Integer id);

}
