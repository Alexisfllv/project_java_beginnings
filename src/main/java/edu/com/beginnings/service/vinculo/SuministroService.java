package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroResponseDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuministroService {
    //metodos
    //listar
    List<SuministroResponseDTO> listarSuministros();

    //listarpaginador
    PaginaRespuestaDTO<SuministroResponseDTO> listarSuministrosPaginados(Pageable pageable);

    //buscarxid
    SuministroResponseDTO buscarSuministro(Integer id);

    //registrar
    SuministroResponseDTO crear(SuministroRequestDTO clienteRequestDTO);

    //registrar con respuesta
    RespuestaDTO crearRespuesta(SuministroRequestDTO clienteRequestDTO);

    //modificar
    SuministroResponseDTO actualizar(SuministroRequestDTO clienteRequestDTO, Integer id);

    //modificar con respuesta
    RespuestaDTO actualizarRespuesta(SuministroRequestDTO clienteRequestDTO, Integer id);

    //eliminar
    void eliminar(Integer id);

    //eliminar respuesta
    RespuestaDTO eliminarRespuesta(Integer id);

}
