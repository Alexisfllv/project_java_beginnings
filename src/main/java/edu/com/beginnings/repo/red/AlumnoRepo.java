package edu.com.beginnings.repo.red;

import edu.com.beginnings.dto.red.AlumnoCursoFlatDTO;
import edu.com.beginnings.model.red.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepo  extends JpaRepository<Alumno, Integer> {

    //edu.com.beginnings.dto.red.AlumnoCursoFlatDTO
    //jpql
    @Query("""
    SELECT new edu.com.beginnings.dto.red.AlumnoCursoFlatDTO(
        a.id, a.nombre, c.id,c.nombre
    )
        FROM Alumno a
            JOIN AlumnoCurso ac ON a.id = ac.id
            JOIN Curso c ON ac.id = c.id
    """)
    List<AlumnoCursoFlatDTO> findAlumnosConCursos();


    // native query

    @Query(value = "SELECT alumnoId, alumnoNombre, cursoId, cursoNombre FROM vw_alumnos_con_cursos", nativeQuery = true)
    List<Object[]> findAlumnosConCursosNative();
}
