package edu.com.beginnings.controller.cadena;



import edu.com.beginnings.dto.record.cadena.DepartamentoRequestDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoResponseDTO;
import edu.com.beginnings.dto.record.cadena.DepartamentoUpdateDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.cadena.DepartamentoService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadena/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {


    //ioc
    private final DepartamentoService departamentoService;


    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<DepartamentoResponseDTO>> listadoDepartamentos() {
        return ResponseEntity.ok(departamentoService.listadoDepartamentos());
    }

    //listar con paginador
    @GetMapping("/listar/pagina")
    public ResponseEntity<PaginaRespuestaDTO<DepartamentoResponseDTO>> listadoDepartamentosPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(departamentoService.listadoDepartamentoPaginado(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<DepartamentoResponseDTO> buscarDepartamento(@PathVariable Integer id) {
        return ResponseEntity.ok(departamentoService.departamentoPorId(id));
    }



    @PostMapping("/registrar")
    public ResponseEntity<DepartamentoResponseDTO> registrarDepartamento(@RequestBody DepartamentoRequestDTO departamentoRequestDTO) {
        DepartamentoResponseDTO registro = departamentoService.registrarDepartamento(departamentoRequestDTO);

        return ResponseEntity.ok(registro);
    }

    //registro con salida en Respuesta

    @PostMapping("/registrar/respuesta")
    public ResponseEntity<RespuestaDTO> registroSalida(@RequestBody DepartamentoRequestDTO departamentoRequestDTO) {
        RespuestaDTO respuestaDTO = departamentoService.registrarDepartamentoMensaje(departamentoRequestDTO);
        return ResponseEntity.ok(respuestaDTO);
    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<DepartamentoResponseDTO> modificarDepartamento(@RequestBody DepartamentoUpdateDTO departamentoUpdateDTO, @PathVariable Integer id) {
        DepartamentoResponseDTO actualizado = departamentoService.actualizarDepartamento(departamentoUpdateDTO, id);
        return ResponseEntity.ok(actualizado);
    }

    //modificar con mensaje
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody DepartamentoUpdateDTO departamentoUpdateDTO,@PathVariable Integer id) {
        RespuestaDTO actualizadores = departamentoService.actualizarDepartamentoMensaje(departamentoUpdateDTO, id);
        return ResponseEntity.ok(actualizadores);
    }


    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarDepartamento(@PathVariable Integer id) {
        departamentoService.eliminarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    //eliminar mensaje
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        RespuestaDTO respuestaDTO= departamentoService.eliminarDepartamentoMensaje(id);

        return ResponseEntity.ok(respuestaDTO);
    }


}
