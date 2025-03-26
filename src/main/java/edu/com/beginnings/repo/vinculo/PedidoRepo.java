package edu.com.beginnings.repo.vinculo;

import edu.com.beginnings.model.vinculo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepo extends JpaRepository<Pedido, Integer> {
}
