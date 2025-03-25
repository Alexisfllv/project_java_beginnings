package edu.com.beginnings.repo.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.model.vinculo.Comida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ComidaRepo extends JpaRepository<Comida, Integer> {


    @Query("SELECT c FROM Comida  c where c.peso > :peso")
    Page<Comida> buscarComidasPesoMayorPesoPaginado(@Param("peso") BigDecimal peso , Pageable pageable);

}
