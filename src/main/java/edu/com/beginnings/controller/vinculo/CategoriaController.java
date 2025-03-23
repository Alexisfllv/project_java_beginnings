package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.CategoriaDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.CategoriaResponseDTO;
import edu.com.beginnings.model.vinculo.Categoria;
import edu.com.beginnings.service.vinculo.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinculo/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    //ioc
    private final CategoriaService categoriaService;

    @GetMapping("/listar")
    public List<CategoriaResponseDTO> listar() {
        return ResponseEntity.ok(categoriaService.listados()).getBody();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscar(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<CategoriaResponseDTO> registrar(@RequestBody CategoriaRequestDTO CategoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.registrar(CategoriaRequestDTO));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaResponseDTO> modificar(@PathVariable Integer id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaResponseDTO modificar = categoriaService.modificar(categoriaDTO,id);
        return ResponseEntity.ok(modificar);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
