package edu.com.beginnings.repo.base;


import edu.com.beginnings.model.base.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepo extends JpaRepository<Libro, Integer> {
}
