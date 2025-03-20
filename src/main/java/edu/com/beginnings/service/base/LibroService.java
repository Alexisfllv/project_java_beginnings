package edu.com.beginnings.service.base;

import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;

import java.util.List;

public interface LibroService {

//    //listado
//    List<Libro> listarLibros();
//
//    //busqueda por id
//    Libro buscarLibro(Integer id);
//
//    //registrar libro
//    Libro guardarLibro(Libro libro);
//
//    //eliminar libro
//    void borrarLibro(Integer id);
//


    // DTO libro
    //listado
    List<LibroResponseDTO> listarLibrosdto();

    //busqueda por id
    LibroResponseDTO buscarDtoResponse(Integer id);

    //registrar libro
    LibroResponseDTO guardarLibrodto(LibroDTO libroDTO);

    //modificar un libro
    LibroResponseDTO modificarunlibrodto(LibroDTO libro , Integer id);

    //eliminar libro
    void borrarLibro(Integer id);



}
