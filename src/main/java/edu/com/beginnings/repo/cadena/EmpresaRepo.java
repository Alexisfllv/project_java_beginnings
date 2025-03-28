package edu.com.beginnings.repo.cadena;


import edu.com.beginnings.model.cadena.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmpresaRepo extends JpaRepository<Empresa, Integer> {
}
