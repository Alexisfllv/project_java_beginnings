package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.dto.record.vinculo.busquedas.ComidaResponsePesoDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ComidaService {

    //listar Comidas con detalles
    List<ComidaResponseDTO> listarComidas();

    //listar Con paginador //poli
    PaginaRespuestaDTO<ComidaResponseDTO> listarComidasp(Pageable pageable);


    //buscar por id de comidas
    ComidaResponseDTO buscarComida(Integer id);

    //metodo de registrar comida
    RespuestaDTO registrarComida(ComidaRequestDTO comidaRequestDTO);

    //modificar comida
    RespuestaDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO, Integer id);
    //ComidaResponseDTO modificarComida(ComidaUpdateDTO comidaUpdateDTO , Integer id);

    //eliminar comiad
    RespuestaDTO eliminarComida(Integer id);

    //filtros jpql
    PaginaRespuestaDTO<ComidaResponsePesoDTO> buscarComidasPesoMayorPesoPaginado(BigDecimal peso ,Pageable pageable);




}
