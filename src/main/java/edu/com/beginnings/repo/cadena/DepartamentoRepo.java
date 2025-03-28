package edu.com.beginnings.repo.cadena;

import edu.com.beginnings.model.cadena.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepo extends JpaRepository<Departamento, Integer> {
}
