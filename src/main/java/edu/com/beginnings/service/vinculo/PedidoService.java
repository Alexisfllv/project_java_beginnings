package edu.com.beginnings.service.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;

import java.util.List;

public interface PedidoService {

    //listar
    List<PedidoResponseDTO> listadoPedidos();

    //buscar x id
    PedidoResponseDTO pedidoPorId(Long id);

    //registrar
    PedidoResponseDTO registrarPedido(PedidoRequestDTO pedidoRequestDTO);

    //modificar
    PedidoResponseDTO actualizarPedido(PedidoUpdateDTO pedidoUpdateDTO);

    //eliminar
    void eliminarPedido(Integer id);
}
