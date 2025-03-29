package edu.com.beginnings.repo.red;


import edu.com.beginnings.model.red.Taller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TallerRepo extends JpaRepository<Taller, Integer> {
}
