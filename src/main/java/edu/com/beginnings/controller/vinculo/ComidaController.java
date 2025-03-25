package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.vinculo.ComidaService;
import edu.com.beginnings.mensaje.RespuestaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
@RestController
@RequestMapping("/vinculo/comidas")
@RequiredArgsConstructor
public class ComidaController {

    //ioc
    private final ComidaService comidaService;


    //listar comidas con detallas
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista completa de usuarios")
    @GetMapping("/listar")
    public ResponseEntity<List<ComidaResponseDTO>> listarComidas() {
        return ResponseEntity.status(200).body(comidaService.listarComidas());
    }

    @GetMapping("/listar-page")
    public ResponseEntity<PaginaRespuestaDTO<ComidaResponseDTO>> listarComidasPaginadas(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(comidaService.listarComidas(pageable));
    }

    // Comida service




    //BUscar x id
    @GetMapping("/buscar/{id}")
    public ComidaResponseDTO buscarComida(@PathVariable Integer id) {
        ComidaResponseDTO comida = comidaService.buscarComida(id);
        return ResponseEntity.status(200).body(comida).getBody();
    }



    @PostMapping("/registrar")
    public ResponseEntity<RespuestaDTO> registrar(@Valid  @RequestBody ComidaRequestDTO comidaRequestDTO) {
        RespuestaDTO response_data = comidaService.registrarComida(comidaRequestDTO);
        return ResponseEntity.status(201).body(response_data);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<RespuestaDTO> modificar(@PathVariable Integer id, @Valid @RequestBody ComidaUpdateDTO comidaUpdateDTO) {

        RespuestaDTO response_data = comidaService.modificarComida(comidaUpdateDTO, id);
        return ResponseEntity.status(200).body(response_data);
    }

    //eliminar

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Integer id) {
        RespuestaDTO response_data = comidaService.eliminarComida(id);
        return ResponseEntity.status(200).body(response_data);
    }

}
