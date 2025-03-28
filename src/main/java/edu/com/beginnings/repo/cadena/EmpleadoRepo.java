package edu.com.beginnings.repo.cadena;


import edu.com.beginnings.model.cadena.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {
}
