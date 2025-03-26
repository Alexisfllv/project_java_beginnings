package edu.com.beginnings.map.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteResponseDTO;
import edu.com.beginnings.model.vinculo.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO toclienteDTO(Cliente cliente);

    Cliente toCliente(ClienteDTO clienteDTO);

    ClienteResponseDTO toClienteResponseDTO(Cliente cliente);

    Cliente toCliente(ClienteResponseDTO clienteResponseDTO);

    ClienteRequestDTO toClienteRequestDTO(Cliente cliente);

    Cliente toCliente(ClienteRequestDTO clienteRequestDTO);


}
