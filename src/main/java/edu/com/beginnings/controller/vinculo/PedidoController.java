package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.model.vinculo.Pedido;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.vinculo.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinculo/pedidos")
@RequiredArgsConstructor
public class PedidoController {


    //ioc
    private final PedidoService pedidoService;


    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<PedidoResponseDTO>> listadoPedidos() {
        return ResponseEntity.ok(pedidoService.listadoPedidos());
    }

    //listar con paginador
    @GetMapping("/listar/page")
    public ResponseEntity<PaginaRespuestaDTO<PedidoResponseDTO>> listadoPedidosPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(pedidoService.listadoPedidoPaginado(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedido(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.pedidoPorId(id));
    }



    @PostMapping("/registro")
    public ResponseEntity<PedidoResponseDTO> registrarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO registro = pedidoService.registrarPedido(pedidoRequestDTO);

        return ResponseEntity.ok(registro);
    }

    //registro con salida en Respuesta

    @PostMapping("/registro/respuesta")
    public ResponseEntity<RespuestaDTO> registroSalida(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        RespuestaDTO respuestaDTO = pedidoService.registrarPedidoMensaje(pedidoRequestDTO);
        return ResponseEntity.ok(respuestaDTO);
    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<PedidoResponseDTO> modificarPedido(@RequestBody PedidoUpdateDTO pedidoUpdateDTO,@PathVariable Integer id) {
        PedidoResponseDTO actualizado = pedidoService.actualizarPedido(pedidoUpdateDTO, id);
        return ResponseEntity.ok(actualizado);
    }

    //modificar con mensaje
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody PedidoUpdateDTO pedidoUpdateDTO,@PathVariable Integer id) {
        RespuestaDTO actualizadores = pedidoService.actualizarPedidoMensaje(pedidoUpdateDTO, id);
        return ResponseEntity.ok(actualizadores);
    }


    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    //eliminar mensaje
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        RespuestaDTO respuestaDTO= pedidoService.eliminarPedidoMensaje(id);

        return ResponseEntity.ok(respuestaDTO);
    }


}
