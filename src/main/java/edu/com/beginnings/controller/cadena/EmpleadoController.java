package edu.com.beginnings.controller.cadena;


import edu.com.beginnings.dto.record.cadena.EmpleadoRequestDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoResponseDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoUpdateDTO;
import edu.com.beginnings.dto.record.cadena.EmpleadoResponseDTO;
import edu.com.beginnings.mensaje.RespuestaDTO;
import edu.com.beginnings.paginador.PaginaRespuestaDTO;
import edu.com.beginnings.service.cadena.EmpleadoService;
import edu.com.beginnings.service.cadena.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadena/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    //ioc
    private final EmpleadoService empleadoService;


    //listar
    @GetMapping("/listar")
    public ResponseEntity<List<EmpleadoResponseDTO>> listadoEmpleados() {
        return ResponseEntity.ok(empleadoService.listadoEmpleados());
    }

    //listar con paginador
    @GetMapping("/listar/pagina")
    public ResponseEntity<PaginaRespuestaDTO<EmpleadoResponseDTO>> listadoEmpleadosPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(empleadoService.listadoEmpleadoPaginado(pageable));
    }

    //busqueda por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<EmpleadoResponseDTO> buscarEmpleado(@PathVariable Integer id) {
        return ResponseEntity.ok(empleadoService.empleadoPorId(id));
    }



    @PostMapping("/registrar")
    public ResponseEntity<EmpleadoResponseDTO> registrarEmpleado(@RequestBody EmpleadoRequestDTO empleadoRequestDTO) {
        EmpleadoResponseDTO registro = empleadoService.registrarEmpleado(empleadoRequestDTO);

        return ResponseEntity.ok(registro);
    }

    //registro con salida en Respuesta

    @PostMapping("/registrar/respuesta")
    public ResponseEntity<RespuestaDTO> registroSalida(@RequestBody EmpleadoRequestDTO empleadoRequestDTO) {
        RespuestaDTO respuestaDTO = empleadoService.registrarEmpleadoMensaje(empleadoRequestDTO);
        return ResponseEntity.ok(respuestaDTO);
    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<EmpleadoResponseDTO> modificarEmpleado(@RequestBody EmpleadoUpdateDTO empleadoUpdateDTO, @PathVariable Integer id) {
        EmpleadoResponseDTO actualizado = empleadoService.actualizarEmpleado(empleadoUpdateDTO, id);
        return ResponseEntity.ok(actualizado);
    }

    //modificar con mensaje
    @PutMapping("/modificar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> modificarRespuesta(@RequestBody EmpleadoUpdateDTO empleadoUpdateDTO,@PathVariable Integer id) {
        RespuestaDTO actualizadores = empleadoService.actualizarEmpleadoMensaje(empleadoUpdateDTO, id);
        return ResponseEntity.ok(actualizadores);
    }


    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //eliminar mensaje
    @DeleteMapping("/eliminar/respuesta/{id}")
    public ResponseEntity<RespuestaDTO> eliminarRespuesta(@PathVariable Integer id) {
        RespuestaDTO respuestaDTO= empleadoService.eliminarEmpleadoMensaje(id);

        return ResponseEntity.ok(respuestaDTO);
    }
}
