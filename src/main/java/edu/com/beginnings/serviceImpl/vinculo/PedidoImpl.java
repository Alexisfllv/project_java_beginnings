package edu.com.beginnings.serviceImpl.vinculo;

import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.vinculo.PedidoMapper;
import edu.com.beginnings.model.vinculo.Cliente;
import edu.com.beginnings.model.vinculo.Pedido;
import edu.com.beginnings.model.vinculo.Suministro;
import edu.com.beginnings.repo.vinculo.ClienteRepo;
import edu.com.beginnings.repo.vinculo.PedidoRepo;
import edu.com.beginnings.repo.vinculo.SuministroRepo;
import edu.com.beginnings.service.vinculo.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoImpl implements PedidoService {

    //ioc
    private final PedidoRepo pedidoRepo;

    //fks
    private final ClienteRepo clienteRepo;

    private final SuministroRepo suministroRepo;

    private final PedidoMapper pedidoMapper;


    @Override
    public List<PedidoResponseDTO> listadoPedidos() {
        return List.of();
    }

    @Override
    public PedidoResponseDTO pedidoPorId(Long id) {
        return null;
    }

    @Override
    public PedidoResponseDTO registrarPedido(PedidoRequestDTO pedidoRequestDTO) {
        // confirmar fks
        Cliente cliente = clienteRepo.findById(pedidoRequestDTO.clienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id Cliente no encontrado : " + pedidoRequestDTO.clienteId()));

        Suministro suministro = suministroRepo.findById(pedidoRequestDTO.suministroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id Suministro no encontrado : " + pedidoRequestDTO.suministroId()));

        //
        Pedido pedido = pedidoMapper.toPedido(pedidoRequestDTO);

        //enviar los registros
        pedido.setCliente(cliente);
        pedido.setSuministro(suministro);

        pedido.setFecha(LocalDate.now());

        pedido = pedidoRepo.save(pedido);

        return pedidoMapper.toPedidoResponseDTO(pedido);

    }

    @Override
    public PedidoResponseDTO actualizarPedido(PedidoUpdateDTO pedidoUpdateDTO) {
        return null;
    }

    @Override
    public void eliminarPedido(Integer id) {

    }
}
