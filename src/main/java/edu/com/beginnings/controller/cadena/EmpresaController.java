package edu.com.beginnings.controller.cadena;


import edu.com.beginnings.dto.record.cadena.EmpresaRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpresaResponseDTO;

import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.cadena.EmpresaService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cadena/empresas")
public class EmpresaController {

    //ioc
    private final EmpresaService empresaService;


    //litado
    @GetMapping("/listar")
    public ResponseEntity<List<EmpresaResponseDTO>> listarEmpresas() {
        return ResponseEntity.status(200).body(empresaService.listarEmpresas());
    }

    //listado paginado
    @GetMapping("/listar/pagina")
    public ResponseEntity<PaginaRespuestaDTO<EmpresaResponseDTO>> listadoempresaPagina(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(empresaService.listarEmpresasPaginados(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarEmpresa(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(empresaService.buscarEmpresa(id));
    }


    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<EmpresaResponseDTO> registrar(@RequestBody EmpresaRequestDTO empresaRequestDTO) {
        return ResponseEntity.status(201).body(empresaService.crear(empresaRequestDTO));
    }

    //registrar con respuesta
    @PostMapping("/registrar/respuesta")
    public ResponseEntity<RespuestaDTO> registrarRespuesta(@RequestBody EmpresaRequestDTO empresaRequestDTO) {
        return ResponseEntity.status(200).body(empresaService.crearRespuesta(empresaRequestDTO));
    }


    @PutMapping("/modificar/{id}")
    public ResponseEntity<EmpresaResponseDTO> modificar(@RequestBody EmpresaRequestDTO empresaRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(empresaService.actualizar(empresaRequestDTO, id));
    }

    //modificar con respuesta
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody EmpresaRequestDTO empresaRequestDTO, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(empresaService.actualizarRespuesta(empresaRequestDTO, id));
    }

    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        empresaService.eliminar(id);
        return ResponseEntity.status(204).build();
    }

    //eliminar con respuesta
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(empresaService.eliminarRespuesta(id));
    }
}
