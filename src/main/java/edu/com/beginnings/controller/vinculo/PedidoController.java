package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoResponseDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.PedidoUpdateDTO;
import edu.com.beginnings.model.vinculo.Pedido;
import edu.com.beginnings.service.vinculo.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vinculo/pedidos")
@RequiredArgsConstructor
public class PedidoController {


    //ioc
    private final PedidoService pedidoService;

    @PostMapping("/registro")
    public ResponseEntity<PedidoResponseDTO> registrarPedido(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO registro = pedidoService.registrarPedido(pedidoRequestDTO);

        return ResponseEntity.ok(registro);
    }
}
