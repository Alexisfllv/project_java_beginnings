package edu.com.beginnings.serviceImpl.red;


import edu.com.beginnings.dto.red.*;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.red.AlumnoMapper;
import edu.com.beginnings.model.red.Alumno;
import edu.com.beginnings.model.red.AlumnoCurso;
import edu.com.beginnings.model.red.Curso;
import edu.com.beginnings.model.red.Taller;
import edu.com.beginnings.repo.red.AlumnoRepo;
import edu.com.beginnings.repo.red.CursoRepo;
import edu.com.beginnings.repo.red.TallerRepo;
import edu.com.beginnings.service.red.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    //repos
    private final AlumnoRepo alumnoRepo;

    private final CursoRepo cursoRepo;
    private final TallerRepo tallerRepo;

    private final AlumnoMapper alumnoMapper;


    @Override
    public List<AlumnoResponseDTO> listarAlumnos() {
        List<Alumno> alumnos = alumnoRepo.findAll();
        return alumnos.stream()
                .map(alumno -> alumnoMapper.toAlumnoResponseDTO(alumno))
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoResponseDTO buscarAlumnoPorId(Integer id) {
        Alumno alumno = alumnoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Alumno Response : " + id + " no encontrado"));
        return alumnoMapper.toAlumnoResponseDTO(alumno);
    }

    @Override
    public AlumnoResponseDTO registrar(AlumnoRequestDTO alumnoRequestDTO) {
        // Crear alumno sin cursos ni talleres
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoRequestDTO.nombre());

        // Recuperar cursos desde BD y asignar relaciones
        List<Curso> cursos = cursoRepo.findAllById(alumnoRequestDTO.cursos());
        List<AlumnoCurso> alumnoCursos = cursos.stream()
                .map(curso -> new AlumnoCurso(null, alumno, curso, "ACTIVO"))
                .toList();

        // Recuperar talleres desde BD
        List<Taller> talleres = tallerRepo.findAllById(alumnoRequestDTO.talleres());

        // Asignar relaciones al alumno
        alumno.setAlumnoCursos(alumnoCursos);
        alumno.setTalleres(talleres);

        // Guardar en BD
        Alumno alumnoGuardado = alumnoRepo.save(alumno);

        // Convertir a DTO de respuesta
        return alumnoMapper.toAlumnoResponseDTO(alumnoGuardado);
    }



}
