package edu.com.beginnings.repo.vinculo;

import edu.com.beginnings.model.vinculo.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepo extends JpaRepository<Comida, Integer> {

}
