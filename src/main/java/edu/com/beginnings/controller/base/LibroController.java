package edu.com.beginnings.controller.base;


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
    public ResponseEntity<List<Libro>> listado() {
        return  ResponseEntity.ok(libroService.listarLibros());
    }

    //buscar

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Libro> buscar(@PathVariable Integer id) {
        return  ResponseEntity.ok(libroService.buscarLibro(id));
    }

    //
    @PostMapping("/registrar")
    public ResponseEntity<Libro> guardar(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.guardarLibro(libro));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        libroService.borrarLibro(id);
        return ResponseEntity.noContent().build();
    }





}
