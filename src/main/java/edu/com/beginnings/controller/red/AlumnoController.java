package edu.com.beginnings.controller.red;


import edu.com.beginnings.dto.record.red.AlumnoGlobalRequestDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalResponseDTO;

import edu.com.beginnings.service.red.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/red/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    //
    private final AlumnoService alumnoService;


    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<AlumnoGlobalResponseDTO>> listarAlumnos() {
        List<AlumnoGlobalResponseDTO> alumnos = alumnoService.listadoAlumnos();
        return ResponseEntity.status(200).body(alumnos);
    }

    //registrar alumno-cursos-tallres
    @PostMapping("/registrar")
    public ResponseEntity<AlumnoGlobalResponseDTO> registrar(@RequestBody AlumnoGlobalRequestDTO alumnoGlobalRequestDTO) {
        AlumnoGlobalResponseDTO alumnoGlobalResponseDTO = alumnoService.crearAlumno(alumnoGlobalRequestDTO);
        return ResponseEntity.status(201).body(alumnoGlobalResponseDTO);
    }

    //buscarId
    @GetMapping("/buscar/{id}")
    public ResponseEntity<AlumnoGlobalResponseDTO> buscar(@PathVariable Integer id) {
        AlumnoGlobalResponseDTO alumnoResponseDTO = alumnoService.buscarAlumnoporid(id);
        return ResponseEntity.status(200).body(alumnoResponseDTO);
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.status(200).build();
    }


}
