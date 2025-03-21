package edu.com.beginnings.controller.base;


import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.model.base.Libro;
import edu.com.beginnings.service.base.LibroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/base/libros")
@Slf4j
public class LibroController {

    //@service bean ioc
    private final LibroService libroService;


    //listar
    @GetMapping("/listado")
    public ResponseEntity<List<LibroResponseDTO>> listado() {
        log.info("REST request para listar todos los libros");
        return ResponseEntity.ok(libroService.listarLibrosdto());
    }

    //buscar por id
    @GetMapping("/buscar/{id}")
    public ResponseEntity<LibroResponseDTO> buscar(@PathVariable Integer id) {
        log.info("REST request para buscar libro con id: {}", id);
        LibroResponseDTO libro = libroService.buscarDtoResponse(id);
        return ResponseEntity.ok(libro);
    }

    //registrar
    @PostMapping("/registrar")
    public ResponseEntity<LibroResponseDTO> registrar( @RequestBody LibroDTO libroDTO) {
        log.info("REST request para registrar un nuevo libro: {}", libroDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(libroService.guardarLibrodto(libroDTO));

    }

    //modificar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<LibroResponseDTO> modificar( @RequestBody LibroDTO libroDTO,@PathVariable Integer id) {

        log.info("REST request para modificar libro con id: {}", id);

        LibroResponseDTO libro =  libroService.modificarLibrodto(libroDTO,id);
        return ResponseEntity.ok(libro);
    }


    //eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("REST request para eliminar libro con id: {}", id);
        libroService.borrarLibro(id);
        return ResponseEntity.noContent().build();
    }


}
