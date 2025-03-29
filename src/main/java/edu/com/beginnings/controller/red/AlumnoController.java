package edu.com.beginnings.controller.red;


import edu.com.beginnings.dto.red.*;
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


    //registrar alumno-cursos-tallres
    @PostMapping("/registrar")
    public ResponseEntity<AlumnoResponseDTO> registrar(@RequestBody AlumnoRequestDTO alumnoRequestDTO) {
        AlumnoResponseDTO alumnoResponseDTO = alumnoService.registrar(alumnoRequestDTO);
        return ResponseEntity.status(201).body(alumnoResponseDTO);
    }

    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<AlumnoResponseDTO>> listarAlumnos() {
        List<AlumnoResponseDTO> alumnos = alumnoService.listarAlumnos();
        return ResponseEntity.status(200).body(alumnos);
    }
}
