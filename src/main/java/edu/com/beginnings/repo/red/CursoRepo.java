package edu.com.beginnings.repo.red;

import edu.com.beginnings.model.red.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Integer> {
}
