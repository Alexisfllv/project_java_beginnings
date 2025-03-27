package edu.com.beginnings.service.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService {

    //listar
    List<PedidoResponseDTO> listadoPedidos();

    //listado con paginador
    PaginaRespuestaDTO<PedidoResponseDTO> listadoPedidoPaginado(Pageable pageable);

    //buscar x id
    PedidoResponseDTO pedidoPorId(Integer id);

    //registrar
    PedidoResponseDTO registrarPedido(PedidoRequestDTO pedidoRequestDTO);

    //registro con mensaje
    RespuestaDTO registrarPedidoMensaje(PedidoRequestDTO pedidoRequestDTO);

    //modificar
    PedidoResponseDTO actualizarPedido(PedidoUpdateDTO pedidoUpdateDTO,Integer id);

    //modificar con mensaje
    RespuestaDTO actualizarPedidoMensaje(PedidoUpdateDTO pedidoUpdateDTO, Integer id);

    //eliminar
    void eliminarPedido(Integer id);

    //eliminar con mensaje
    RespuestaDTO eliminarPedidoMensaje(Integer id);
}
