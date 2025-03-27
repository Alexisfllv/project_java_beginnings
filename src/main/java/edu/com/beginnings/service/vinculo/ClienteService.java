package edu.com.beginnings.service.vinculo;

import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteResponseDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.vinculo.Cliente;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    //metodos
    //listar
    List<ClienteResponseDTO> listarClientes();

    //listarpaginador
    PaginaRespuestaDTO<ClienteResponseDTO> listarClientesPaginados(Pageable pageable);

    //buscarxid
    ClienteResponseDTO buscarCliente(Integer id);

    //registrar
    ClienteResponseDTO crear(ClienteRequestDTO clienteRequestDTO);

    //registrar con respuesta
    RespuestaDTO crearRespuesta(ClienteRequestDTO clienteRequestDTO);

    //modificar
    ClienteResponseDTO actualizar(ClienteRequestDTO clienteRequestDTO, Integer id);

    //modificar con respuesta
    RespuestaDTO actualizarRespuesta(ClienteRequestDTO clienteRequestDTO, Integer id);

    //eliminar
    void eliminar(Integer id);

    //eliminar respuesta
    RespuestaDTO eliminarRespuesta(Integer id);


}
