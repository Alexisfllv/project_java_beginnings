package edu.com.beginnings.repo.base;

import edu.com.beginnings.model.base.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {
}
