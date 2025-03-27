package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.ClienteResponseDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.vinculo.ClienteService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vinculo/clientes")
public class ClienteController {

    //ioc
    private final ClienteService clienteService;


    //litado
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        return ResponseEntity.status(200).body(clienteService.listarClientes());
    }

    //listado paginado
    @GetMapping("/listar/pagina")
    public ResponseEntity<PaginaRespuestaDTO<ClienteResponseDTO>> listadoclientePagina(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(clienteService.listarClientesPaginados(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarCliente(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(clienteService.buscarCliente(id));
    }


    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponseDTO> registrar(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(201).body(clienteService.crear(clienteRequestDTO));
    }

    //registrar con respuesta
    @PostMapping("/registrar/respuesta")
    public ResponseEntity<RespuestaDTO> registrarRespuesta(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        return ResponseEntity.status(200).body(clienteService.crearRespuesta(clienteRequestDTO));
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<ClienteResponseDTO> modificar(@RequestBody ClienteRequestDTO clienteRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(clienteService.actualizar(clienteRequestDTO, id));
    }

    //modificar con respuesta
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody ClienteRequestDTO clienteRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(clienteService.actualizarRespuesta(clienteRequestDTO, id));
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.status(204).build();
    }

    //eliminar con respuesta
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(clienteService.eliminarRespuesta(id));
    }
}
