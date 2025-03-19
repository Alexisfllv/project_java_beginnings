package edu.com.beginnings.service.base;

import edu.com.beginnings.model.base.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroService {

    //listado
    List<Libro> listarLibros();

    //busqueda por id
    Libro buscarLibro(Integer id);

    //registrar libro
    Libro guardarLibro(Libro libro);

    //eliminar libro
    void borrarLibro(Integer id);
}
