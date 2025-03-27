package edu.com.beginnings.controller.vinculo;


import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroRequestDTO;
import edu.com.beginnings.dto.record.vinculo.pedidos.SuministroResponseDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.vinculo.SuministroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vinculo/suministros")
public class SuministroController {

    //ioc
    private final SuministroService suministroService;


    //litado
    @GetMapping("/listar")
    public ResponseEntity<List<SuministroResponseDTO>> listarSuministros() {
        return ResponseEntity.status(200).body(suministroService.listarSuministros());
    }

    //listado paginado
    @GetMapping("/listar/pagina")
    public ResponseEntity<PaginaRespuestaDTO<SuministroResponseDTO>> listadosuministroPagina(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(suministroService.listarSuministrosPaginados(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<SuministroResponseDTO> buscarSuministro(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(suministroService.buscarSuministro(id));
    }


    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<SuministroResponseDTO> registrar(@RequestBody SuministroRequestDTO suministroRequestDTO) {
        return ResponseEntity.status(201).body(suministroService.crear(suministroRequestDTO));
    }

    //registrar con respuesta
    @PostMapping("/registrar/respuesta")
    public ResponseEntity<RespuestaDTO> registrarRespuesta(@RequestBody SuministroRequestDTO suministroRequestDTO) {
        return ResponseEntity.status(200).body(suministroService.crearRespuesta(suministroRequestDTO));
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<SuministroResponseDTO> modificar(@RequestBody SuministroRequestDTO suministroRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(suministroService.actualizar(suministroRequestDTO, id));
    }

    //modificar con respuesta
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody SuministroRequestDTO suministroRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(suministroService.actualizarRespuesta(suministroRequestDTO, id));
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        suministroService.eliminar(id);
        return ResponseEntity.status(204).build();
    }

    //eliminar con respuesta
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(suministroService.eliminarRespuesta(id));
    }
}
