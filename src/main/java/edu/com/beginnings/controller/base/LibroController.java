package edu.com.beginnings.controller.base;


import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.model.base.Libro;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/base/libros")
public class LibroController {

    //@service bean ioc
    private final LibroService libroService;


    //listar
    @GetMapping("/listado")
    public ResponseEntity<List<LibroResponseDTO>> listado() {
        return ResponseEntity.ok(libroService.listarLibrosdto());
    }

    //buscar por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<LibroResponseDTO> buscar(@PathVariable Integer id) {
        LibroResponseDTO libro = libroService.buscarDtoResponse(id);
        return ResponseEntity.ok(libro);
    }

    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<LibroResponseDTO> registrar(@RequestBody LibroDTO libroDTO) {
        return  ResponseEntity.ok(libroService.guardarLibrodto(libroDTO));
    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<LibroResponseDTO> modificar( @RequestBody LibroDTO libroDTO,@PathVariable Integer id) {
        LibroResponseDTO libro2 =  libroService.modificarunlibrodto(libroDTO,id);
        return ResponseEntity.ok(libro2);
    }


    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        libroService.borrarLibro(id);
        return ResponseEntity.ok().build();
    }






}
