package edu.com.beginnings.serviceImpl.vinculo;

import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseEliminarDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.vinculo.PedidoMapper;
import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.vinculo.Cliente;
import edu.com.beginnings.model.vinculo.Pedido;
import edu.com.beginnings.model.vinculo.Suministro;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.vinculo.ClienteRepo;
import edu.com.beginnings.repo.vinculo.PedidoRepo;
import edu.com.beginnings.repo.vinculo.SuministroRepo;
import edu.com.beginnings.service.vinculo.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    //ioc
    private final PedidoRepo pedidoRepo;

    //fks
    private final ClienteRepo clienteRepo;

    private final SuministroRepo suministroRepo;

    private final PedidoMapper pedidoMapper;


    @Override
    public List<PedidoResponseDTO> listadoPedidos() {

        List<Pedido> pedidos = pedidoRepo.findAll();

        return pedidos.stream()
                .map(pedido -> pedidoMapper.toPedidoResponseDTO(pedido))
                .collect(Collectors.toList());

    }


    @Override
    public PaginaRespuestaDTO<PedidoResponseDTO> listadoPedidoPaginado(Pageable pageable) {

        Page<PedidoResponseDTO> paginar = pedidoRepo.findAll(pageable)
                .map(pedido -> pedidoMapper.toPedidoResponseDTO(pedido));

        //
        return new PaginaRespuestaDTO<PedidoResponseDTO>(paginar);

    }

    @Override
    public PedidoResponseDTO pedidoPorId(Integer id) {
        Pedido pedido =  pedidoRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Pedido no encontrado : " + id));

        return pedidoMapper.toPedidoResponseDTO(pedido);

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
    public RespuestaDTO registrarPedidoMensaje(PedidoRequestDTO pedidoRequestDTO) {


        //verificar fks
        Cliente cliente = clienteRepo.findById(pedidoRequestDTO.clienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id Cliente no encontrado : " + pedidoRequestDTO.clienteId()));

        Suministro suministro =  suministroRepo.findById(pedidoRequestDTO.suministroId())
                .orElseThrow(() -> new RecursoNoEncontradoException("id Suministro no encontado : " + pedidoRequestDTO.suministroId()));

        //Convertir al modelo
        Pedido pedido = pedidoMapper.toPedido(pedidoRequestDTO);

        //registrar las fk
        pedido.setCliente(cliente);
        pedido.setSuministro(suministro);

        //resto de datos // depende de la logica de negocio
        pedido.setFecha(LocalDate.now());

        //guardar con JPA
        pedido = pedidoRepo.save(pedido);

        //regresar al responseDTO
        PedidoResponseDTO registro = pedidoMapper.toPedidoResponseDTO(pedido);

        //
        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(), registro);
    }

    @Override
    public PedidoResponseDTO actualizarPedido(PedidoUpdateDTO pedidoUpdateDTO,Integer id) {

        //Buscar duplicado de id pedido
        Pedido pedido =  pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado : " + id));

        //actualizar datos o dejarlos como eran anteriormente
        if (pedidoUpdateDTO.fecha() != null) {
            pedido.setFecha(pedidoUpdateDTO.fecha());
        }
        if (pedidoUpdateDTO.total() != null) {
            pedido.setTotal(pedidoUpdateDTO.total());
        }
        if (pedidoUpdateDTO.estado() != null) {
            pedido.setEstado(pedidoUpdateDTO.estado());
        }

        // ingreso de la fks
        //cliente // suministro
        if (pedidoUpdateDTO.clienteId() != null) {
            Cliente cliente = clienteRepo.findById(pedidoUpdateDTO.clienteId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Categoria no encontrado : " + id));
            pedido.setCliente(cliente);
        }

        //
        if (pedidoUpdateDTO.suministroId() != null) {
            Suministro suministro = suministroRepo.findById(pedidoUpdateDTO.suministroId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Suministro no encontrado : " + id));
        }

        //guardar cambios
        pedido = pedidoRepo.save(pedido);

        return pedidoMapper.toPedidoResponseDTO(pedido);

    }

    @Override
    public RespuestaDTO actualizarPedidoMensaje(PedidoUpdateDTO pedidoUpdateDTO, Integer id) {

        //Buscar duplicado de id pedido
        Pedido pedido =  pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado : " + id));

        //actualizar datos o dejarlos como eran anteriormente
        if (pedidoUpdateDTO.fecha() != null) {
            pedido.setFecha(pedidoUpdateDTO.fecha());
        }
        if (pedidoUpdateDTO.total() != null) {
            pedido.setTotal(pedidoUpdateDTO.total());
        }
        if (pedidoUpdateDTO.estado() != null) {
            pedido.setEstado(pedidoUpdateDTO.estado());
        }

        // ingreso de la fks
        //cliente // suministro
        if (pedidoUpdateDTO.clienteId() != null) {
            Cliente cliente = clienteRepo.findById(pedidoUpdateDTO.clienteId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Categoria no encontrado : " + id));
            pedido.setCliente(cliente);
        }

        //
        if (pedidoUpdateDTO.suministroId() != null) {
            Suministro suministro = suministroRepo.findById(pedidoUpdateDTO.suministroId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("id Suministro no encontrado : " + id));
        }

        //guardar cambios
        pedido = pedidoRepo.save(pedido);

        //tranformar a PedidoResponseDTO
        PedidoResponseDTO pedidoResponseDTO = pedidoMapper.toPedidoResponseDTO(pedido);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(), pedidoResponseDTO);
    }

    @Override
    public void eliminarPedido(Integer id) {

        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado : " + id) );

        pedidoRepo.delete(pedido);
    }

    @Override
    public RespuestaDTO eliminarPedidoMensaje(Integer id) {

        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado : " + id) );

        pedidoRepo.delete(pedido);

        //mapeo
        PedidoResponseEliminarDTO pedidoResponseEliminarDTO =
                new PedidoResponseEliminarDTO(pedido.getId(),pedido.getFecha());

        //mensaje
        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), pedidoResponseEliminarDTO);
    }
}
