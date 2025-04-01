package edu.com.beginnings.repo.red;

import edu.com.beginnings.model.red.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepo  extends JpaRepository<Alumno, Integer> {


}
