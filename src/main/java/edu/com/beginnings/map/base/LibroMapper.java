package edu.com.beginnings.map.base;


import edu.com.beginnings.dto.base.LibroDTO;
import edu.com.beginnings.dto.base.LibroResponseDTO;
import edu.com.beginnings.model.base.Libro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    // > DTO
    LibroDTO toDTO(Libro libro);
    // > Model
    Libro toLibro(LibroDTO libroDTO);

    // 2
    LibroResponseDTO toResponseDTO(Libro libro);
    //
    Libro toLibro2(LibroResponseDTO libroResponseDTO);

}
