package edu.com.beginnings.map.vinculo;

import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseEliminarDTO;
import edu.com.beginnings.model.vinculo.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    //
    PedidoDTO toPedidoDTO(Pedido pedido);
    Pedido toPedido(PedidoDTO pedidoDTO);

    PedidoResponseDTO toPedidoResponseDTO(Pedido pedido);
    Pedido toPedido(PedidoResponseDTO pedidoResponseDTO);

    ComidaRequestDTO toPedidoRequestDTO(Pedido pedido);

    @Mapping(target = "suministro.id" , source = "suministroId")
    @Mapping(target = "cliente.id", source = "clienteId")
    Pedido toPedido(PedidoRequestDTO pedidoRequestDTO);

    //vista de eliminar
    PedidoResponseEliminarDTO toPedidoResponseEliminarDTO(Pedido pedido);
    Pedido toPedido(PedidoResponseEliminarDTO pedidoResponseEliminarDTO);
}
