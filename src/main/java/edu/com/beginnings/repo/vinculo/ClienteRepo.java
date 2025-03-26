package edu.com.beginnings.repo.vinculo;

import edu.com.beginnings.model.vinculo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepo  extends JpaRepository<Cliente, Integer> {
}
