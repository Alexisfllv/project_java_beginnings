package edu.com.beginnings.serviceImpl.red;


import edu.com.beginnings.dto.record.red.AlumnoGlobalRequestDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalResponseDTO;
import edu.com.beginnings.dto.record.red.AlumnoGlobalUpdateDTO;

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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    //repo
    private final AlumnoRepo alumnoRepo;

    private final CursoRepo cursoRepo;
    private final TallerRepo tallerRepo;

    //map
    private final AlumnoMapper alumnoMapper;

    @Override
    public List<AlumnoGlobalResponseDTO> listadoAlumnos() {
        List<Alumno> alumnos = alumnoRepo.findAll();
        return alumnos.stream()
                .map(alumnoMapper::toAlumnoGlobalResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlumnoGlobalResponseDTO buscarAlumnoporid(Integer id) {
        Alumno alumno = alumnoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("alumno no encontrado : " +id));
        return alumnoMapper.toAlumnoGlobalResponseDTO(alumno);
    }

    @Transactional
    @Override
    public AlumnoGlobalResponseDTO crearAlumno(AlumnoGlobalRequestDTO alumnoGlobalRequestDTO) {
        //
        Alumno alumno = new Alumno();
        alumno.setNombre(alumnoGlobalRequestDTO.nombre());

        // entidad curso
        List<Curso> cursos = alumnoGlobalRequestDTO.cursosIds()
                .stream()
                .map(id -> cursoRepo.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontado ID : " + id)))
                .toList();

        // asignar curso
        List<AlumnoCurso> alumnoCursos = cursos
                .stream()
                .map(curso -> new AlumnoCurso(null,alumno,curso,"ACTIVO"))
                .collect(Collectors.toList());
        alumno.setAlumnoCursos(alumnoCursos);

        //entidad taller
        List<Taller> talleres = alumnoGlobalRequestDTO.talleresIds()
                .stream()
                .map(id -> tallerRepo.findById(id)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Taller no encontrado ID : " + id)))
                .toList();

        //guardar
        alumno.setTalleres(talleres);

        //global
        alumnoRepo.save(alumno);

        return alumnoMapper.toAlumnoGlobalResponseDTO(alumno);
    }

    @Override
    @Transactional
    public AlumnoGlobalResponseDTO modificarAlumno(Integer id, AlumnoGlobalUpdateDTO alumnoGlobalUpdateDTO) {

        //validar id
        Alumno alumno = alumnoRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Alumno no encontrado : " +id));

        // envidar datos
        alumno.setNombre(alumnoGlobalUpdateDTO.nombre());

        // Convertir los cursos actuales en un Set de Ids para evitar duplicados
        Set<Integer> cursosexistentes = alumno.getAlumnoCursos()
                .stream()
                .map(ac -> ac.getCurso().getId())
                .collect(Collectors.toSet());

        // agregar solo los cursos que aun no estan registrados
        List<AlumnoCurso> nuevosAlumnosCursos = alumnoGlobalUpdateDTO.cursosIds()
                .stream()
                .filter(idcurso -> !cursosexistentes.contains(idcurso)) //evitar duplicados
                .map(idcurso -> {
                    Curso curso = cursoRepo.findById(idcurso)
                            .orElseThrow( () -> new RecursoNoEncontradoException("Curso no encontrado ID : " + idcurso));
                    return new AlumnoCurso(null,alumno,curso,"ACTIVO");
                })
                .toList();

        alumno.getAlumnoCursos().addAll(nuevosAlumnosCursos); // agregar nuevos cursos sin borrar existentes

        // Convertir los talleres actuales enun Set de Ids
        Set<Integer> talleresExistentes = alumno.getTalleres()
                .stream()
                .map(Taller -> Taller.getId())
                .collect(Collectors.toSet());

        //agregar solo los talleres que no estan registrados
        List<Taller> nuevosTalleres = alumnoGlobalUpdateDTO.talleresIds()
                .stream()
                .filter(idTaller ->!talleresExistentes.contains(idTaller)) //evita duplicados
                .map(idTaller -> tallerRepo.findById(idTaller)
                        .orElseThrow(() -> new RecursoNoEncontradoException("Taller no encontrado Id : " +idTaller )))
                .toList();

        alumno.getTalleres().addAll(nuevosTalleres); // agregar nuevos sin borrar existentes

        alumnoRepo.save(alumno);


        return new AlumnoGlobalResponseDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getAlumnoCursos().stream().map(ac -> ac.getCurso().getNombre()).toList(),
                alumno.getTalleres().stream().map(Taller::getNombre).toList()
        );
    }

    @Override
    public void eliminarAlumno(Integer id) {
            if (!alumnoRepo.existsById(id)) {
                throw new RecursoNoEncontradoException("Alumno no encontrado : " + id);
            }
            alumnoRepo.deleteById(id);
    }

    //







}
