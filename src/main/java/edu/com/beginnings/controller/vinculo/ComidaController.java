package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaUpdateDTO;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.service.vinculo.ComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vinculo/comidas")
@RequiredArgsConstructor
public class ComidaController {

    //ioc
    private final ComidaService comidaService;


    //listar comidas con detallas
    @GetMapping("/listar")
    public ResponseEntity<?> listarComidas(

            //paginador
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size

    ) {
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(page, size);
            return ResponseEntity.ok(comidaService.listarComidas(pageable));
        }
        return ResponseEntity.status(200).body(comidaService.listarComidas());
    }

    // Comida service




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
    public ResponseEntity<Map<String, Object>> modificar(@PathVariable Integer id, @Valid @RequestBody ComidaUpdateDTO comidaUpdateDTO) {

        Map<String, Object> respuestayresponse = comidaService.modificarComida(comidaUpdateDTO, id);
        return ResponseEntity.status(200).body(respuestayresponse);
    }

    //eliminar

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        comidaService.eliminarComida(id);
        return ResponseEntity.status(204).build();
    }

}
