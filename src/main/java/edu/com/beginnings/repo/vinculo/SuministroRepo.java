package edu.com.beginnings.repo.vinculo;


import edu.com.beginnings.model.vinculo.Suministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuministroRepo extends JpaRepository<Suministro, Integer> {
}
