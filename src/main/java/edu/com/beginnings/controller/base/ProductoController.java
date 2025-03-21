package edu.com.beginnings.controller.base;


import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.dto.base.ProductoDTO;
import edu.com.beginnings.dto.base.ProductoRequestDTO;
import edu.com.beginnings.dto.base.ProductoResponseDTO;
import edu.com.beginnings.service.base.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/base/productos")
public class ProductoController {

    //ioc
    private final ProductoService productoService;

    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarLibros());
    }

    //buscar por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.buscarLibro(id));
    }

    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<ProductoResponseDTO> registrar(@RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.ok(productoService.registrar(productoRequestDTO));
    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductoResponseDTO> modificar(@RequestBody ProductoDTO productoDTO, @PathVariable Integer id) {
        ProductoResponseDTO modificado = productoService.actualizar(productoDTO,id);
        return ResponseEntity.ok(modificado);
    }

    //eliminar

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
