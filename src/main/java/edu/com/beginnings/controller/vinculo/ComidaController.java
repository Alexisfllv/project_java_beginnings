package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.service.vinculo.ComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vinculo/comidas")
@RequiredArgsConstructor
public class ComidaController {

    //ioc
    private final ComidaService comidaService;


    //listar comidas con detallas
    @GetMapping("/listar")
    public List<ComidaResponseDTO> listarComidas() {
        List<ComidaResponseDTO> comidas = comidaService.listarComidas();
        return ResponseEntity.status(200).body(comidas).getBody();
    }

    //BUscar x id
    @GetMapping("/buscar/{id}")
    public ComidaResponseDTO buscarComida(@PathVariable Integer id) {
        ComidaResponseDTO comida = comidaService.buscarComida(id);
        return ResponseEntity.status(200).body(comida).getBody();
    }



    @PostMapping("/registrar")
    public ResponseEntity<ComidaResponseDTO> registrar(@Valid  @RequestBody ComidaRequestDTO comidaRequestDTO) {
        ComidaResponseDTO registro = comidaService.registrarComida(comidaRequestDTO);
        return ResponseEntity.status(201).body(registro);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ComidaResponseDTO> modificar(@PathVariable Integer id, @Valid @RequestBody ComidaUpdateDTO comidaUpdateDTO) {

        ComidaResponseDTO modificado =  comidaService.modificarComida(comidaUpdateDTO,id);
        return ResponseEntity.status(200).body(modificado);
    }

    //eliminar

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comidaService.eliminarComida(id);
        return ResponseEntity.status(204).build();
    }

}
