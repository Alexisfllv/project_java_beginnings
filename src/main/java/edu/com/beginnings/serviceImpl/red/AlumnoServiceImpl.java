package edu.com.beginnings.serviceImpl.red;


import edu.com.beginnings.dto.red.AlumnoConCursosResponseDTO;
import edu.com.beginnings.dto.red.AlumnoCursoFlatDTO;
import edu.com.beginnings.dto.red.CursoDTO;
import edu.com.beginnings.map.red.AlumnoMapper;
import edu.com.beginnings.model.red.Alumno;
import edu.com.beginnings.repo.red.AlumnoRepo;
import edu.com.beginnings.service.red.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepo alumnoRepo;

    private final AlumnoMapper alumnoMapper;


    @Override
    public List<AlumnoConCursosResponseDTO> listarAlumnosConCursos() {

        List<Alumno> alumnos = alumnoRepo.findAll();

        return alumnos
                .stream()
                .map(alumnocurso -> alumnoMapper.toAlumnoConCursosResponseDTO(alumnocurso))
                .collect(Collectors.toList());

    }

    @Override
    public AlumnoConCursosResponseDTO buscarAlumnoConCurso(Integer id) {
        return null;
    }

    @Override
    public List<AlumnoConCursosResponseDTO> findAlumnosConCursosPorCurso() {

        return alumnoRepo.findAlumnosConCursos().stream()
                .collect(Collectors.groupingBy(
                        AlumnoCursoFlatDTO::alumnoId,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista -> new AlumnoConCursosResponseDTO(
                                        lista.get(0).alumnoId(),
                                        lista.get(0).alumnoNombre(),
                                        lista.stream()
                                                .map(f -> new CursoDTO(f.cursoId(), f.cursoNombre()))
                                                .toList()
                                )
                        )
                ))
                .values()
                .stream()
                .toList();
    }



    @Override
    public List<AlumnoConCursosResponseDTO> findAlumnosConCursosPorCursoNative() {
        List<Object[]> resultados = alumnoRepo.findAlumnosConCursosNative();

        // Convertir Object[] a AlumnoCursoFlatDTO
        List<AlumnoCursoFlatDTO> listaFlat = resultados.stream()
                .map(obj -> new AlumnoCursoFlatDTO(
                        (Integer) obj[0],  // alumnoId
                        (String) obj[1],   // alumnoNombre
                        (Integer) obj[2],  // cursoId
                        (String) obj[3]    // cursoNombre
                ))
                .toList();

        // Agrupar por alumnoId y construir la respuesta final
        return listaFlat.stream()
                .collect(Collectors.groupingBy(
                        AlumnoCursoFlatDTO::alumnoId,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                lista -> new AlumnoConCursosResponseDTO(
                                        lista.get(0).alumnoId(),
                                        lista.get(0).alumnoNombre(),
                                        lista.stream()
                                                .map(f -> new CursoDTO(f.cursoId(), f.cursoNombre()))
                                                .toList()
                                )
                        )
                ))
                .values()
                .stream()
                .toList();
    }


}
