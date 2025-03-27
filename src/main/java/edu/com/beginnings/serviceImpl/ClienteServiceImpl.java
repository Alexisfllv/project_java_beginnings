package edu.com.beginnings.serviceImpl;

import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteResponseDTO;
import edu.com.beginnings.excepcion.errores.RecursoNoEncontradoException;
import edu.com.beginnings.map.vinculo.ClienteMapper;
import edu.com.beginnings.mensaje.MensajeRespuesta;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.vinculo.Cliente;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.repo.vinculo.ClienteRepo;
import edu.com.beginnings.service.vinculo.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    //ioc
    private final ClienteRepo clienteRepo;

    private final ClienteMapper clienteMapper;


    @Override
    public List<ClienteResponseDTO> listarClientes() {
        List<Cliente> clientes = clienteRepo.findAll();

        return clientes.stream()
                .map(cliente -> clienteMapper.toClienteResponseDTO(cliente))
                .collect(Collectors.toList());

    }

    @Override
    public PaginaRespuestaDTO<ClienteResponseDTO> listarClientesPaginados(Pageable pageable) {
        Page<ClienteResponseDTO> paginar = clienteRepo.findAll(pageable)
                .map(cliente -> clienteMapper.toClienteResponseDTO(cliente));
        //
        return new PaginaRespuestaDTO<>(paginar);
    }

    @Override
    public ClienteResponseDTO buscarCliente(Integer id) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Cliente no encontrado : " + id));

        return clienteMapper.toClienteResponseDTO(cliente);
    }

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toCliente(clienteRequestDTO);

        //logica

        cliente = clienteRepo.save(cliente);
        return clienteMapper.toClienteResponseDTO(cliente);
    }

    @Override
    public RespuestaDTO crearRespuesta(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toCliente(clienteRequestDTO);

        cliente = clienteRepo.save(cliente);

        ClienteResponseDTO clienteResponseDTO = clienteMapper.toClienteResponseDTO(cliente);

        return new RespuestaDTO(MensajeRespuesta.AGREGADO_EXITOSO.getMensaje(),clienteResponseDTO);
    }

    @Override
    public ClienteResponseDTO actualizar(ClienteRequestDTO clienteRequestDTO, Integer id) {
        //
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Cliente no encontrado : " + id));

        //valores de ingreso
        if(clienteRequestDTO.nombre() != null){
            cliente.setNombre(clienteRequestDTO.nombre());
        }

        if (clienteRequestDTO.email() != null){
            cliente.setEmail(clienteRequestDTO.email());
        }

        if (clienteRequestDTO.telefono() != null){
            cliente.setTelefono(clienteRequestDTO.telefono());
        }

        cliente = clienteRepo.save(cliente);

        return clienteMapper.toClienteResponseDTO(cliente);
    }

    @Override
    public RespuestaDTO actualizarRespuesta(ClienteRequestDTO clienteRequestDTO, Integer id) {

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado : " + id));

        //
        if(clienteRequestDTO.nombre() != null){
            cliente.setNombre(clienteRequestDTO.nombre());
        }
        if (clienteRequestDTO.email() != null){
            cliente.setEmail(clienteRequestDTO.email());
        }
        if (clienteRequestDTO.telefono() != null){
            cliente.setTelefono(clienteRequestDTO.telefono());
        }

        cliente = clienteRepo.save(cliente);
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toClienteResponseDTO(cliente);

        return new RespuestaDTO(MensajeRespuesta.MODIFICACION_EXITOSA.getMensaje(),clienteResponseDTO);

    }

    @Override
    public void eliminar(Integer id) {

        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado : " + id));

        clienteRepo.delete(cliente);
    }

    @Override
    public RespuestaDTO eliminarRespuesta(Integer id) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado : " + id));

        clienteRepo.delete(cliente);

        return new RespuestaDTO(MensajeRespuesta.ELIMINACION_EXITOSA.getMensaje(), "Id cliente eliminado : " +id);
    }


}
