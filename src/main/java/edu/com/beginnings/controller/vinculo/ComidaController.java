package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.ComidaRequestDTO;
import edu.com.beginnings.dto.record.vinculo.ComidaResponseDTO;
import edu.com.beginnings.model.vinculo.Comida;
import edu.com.beginnings.service.vinculo.ComidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vinculo/comidas")
@RequiredArgsConstructor
public class ComidaController {

    //ioc
    private final ComidaService comidaService;

    @PostMapping("/registrar")
    public ResponseEntity<ComidaResponseDTO> registrar(@Valid  @RequestBody ComidaRequestDTO comidaRequestDTO) {
        ComidaResponseDTO registro = comidaService.registrarComida(comidaRequestDTO);
        return ResponseEntity.status(201).body(registro);
    }
}
