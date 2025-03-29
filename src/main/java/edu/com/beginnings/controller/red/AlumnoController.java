package edu.com.beginnings.controller.red;


import edu.com.beginnings.dto.red.AlumnoConCursosResponseDTO;
import edu.com.beginnings.dto.red.AlumnoConCursosYTalleresResponseDTO;
import edu.com.beginnings.dto.red.AlumnoConTalleresResponseDTO;
import edu.com.beginnings.service.red.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/red/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    //
    private final AlumnoService alumnoService;

    @GetMapping("/listar")
    public ResponseEntity<List<AlumnoConCursosResponseDTO>> listarAlumnos() {
        List<AlumnoConCursosResponseDTO> listado = alumnoService.listarAlumnosConCursos();
        return ResponseEntity.status(200).body(listado);
    }

    //listado con jpql
    @GetMapping("/listar/jpql")
    public ResponseEntity<List<AlumnoConCursosResponseDTO>> listarJpql() {
        List<AlumnoConCursosResponseDTO> listado = alumnoService.findAlumnosConCursosPorCurso();
        return ResponseEntity.status(200).body(listado);
    }

    //listado con view
    @GetMapping("/listar/view")
    public ResponseEntity<List<AlumnoConCursosResponseDTO>> listarView() {
        List<AlumnoConCursosResponseDTO> listado = alumnoService.findAlumnosConCursosPorCursoNative();
        return ResponseEntity.status(200).body(listado);
    }

    //listado alumno-talleres
    @GetMapping("/listar-talleres")
    public ResponseEntity<List<AlumnoConTalleresResponseDTO>> listarTalleres() {
        List<AlumnoConTalleresResponseDTO> listado = alumnoService.listarAlumnosConTalleres();
        return ResponseEntity.status(200).body(listado);
    }

    //listado alumno-curso-talleres
    @GetMapping("/listar-cursos-talleres")
    public ResponseEntity<List<AlumnoConCursosYTalleresResponseDTO>> listarCursosTalleres() {
        List<AlumnoConCursosYTalleresResponseDTO> listado2 = alumnoService.listadoCursoYTalleres();
        return ResponseEntity.status(200).body(listado2);
    }
}
